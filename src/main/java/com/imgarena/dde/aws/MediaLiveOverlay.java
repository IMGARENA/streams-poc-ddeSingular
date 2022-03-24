package com.imgarena.dde.aws;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.medialive.MediaLiveAsyncClient;
import software.amazon.awssdk.services.medialive.model.BatchUpdateScheduleRequest;
import software.amazon.awssdk.utils.builder.SdkBuilder;

@Service
public class MediaLiveOverlay {

  private static final Logger LOG = LoggerFactory.getLogger(MediaLiveOverlay.class);

  private final MediaLiveAsyncClient client;

  public MediaLiveOverlay(MediaLiveAsyncClient client) {
    this.client = client;
  }

  public void insertOverlay(String channelId, String overlayURL) {
    LOG.info("Inserting graphic overlay in MediaLive channel={}", channelId);
    var batchUpdateScheduleRequest =
        BatchUpdateScheduleRequest.builder()
            .channelId(channelId)
            .creates(
                builder ->
                    builder
                        .scheduleActions(
                            builder1 ->
                                builder1
                                    .actionName("ActivateScoreBug")
                                    .scheduleActionStartSettings(
                                        builder2 ->
                                            builder2.immediateModeScheduleActionStartSettings(
                                                SdkBuilder::build))
                                    .scheduleActionSettings(
                                        builder2 ->
                                            builder2.motionGraphicsImageActivateSettings(
                                                builder3 -> builder3.url(overlayURL).build()))
                                    .build())
                        .build())
            .build();
    try {
      client.batchUpdateSchedule(batchUpdateScheduleRequest).join();
    } catch (Exception e) {
      LOG.warn(
          "Error adding graphic overlay to MediaLive channel={} due to {}",
          channelId,
          ExceptionUtils.getRootCauseMessage(e));
    }
  }

  public void deleteOverlay(String channelId) {
    LOG.info("Deleting graphic overlay for MediaLive channel={}", channelId);
    var batchUpdateScheduleRequest =
        BatchUpdateScheduleRequest.builder()
            .channelId(channelId)
            .creates(
                builder ->
                    builder
                        .scheduleActions(
                            builder1 ->
                                builder1
                                    .actionName("DeactivateScoreBug")
                                    .scheduleActionStartSettings(
                                        builder2 ->
                                            builder2.immediateModeScheduleActionStartSettings(SdkBuilder::build))
                                    .scheduleActionSettings(
                                        builder2 ->
                                            builder2.motionGraphicsImageDeactivateSettings(SdkBuilder::build))
                                    .build())
                        .build())
            .build();
    try {
      client.batchUpdateSchedule(batchUpdateScheduleRequest).join();
    } catch (Exception e) {
      LOG.warn(
          "Error deleting graphic overlay from MediaLive channel={} due to {}",
          channelId,
          ExceptionUtils.getRootCauseMessage(e));
    }
  }
}
