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

  private static final String ACTIVATE_ACTION = "ActivateScoreBug_%d";
  private static final String DEACTIVATE_ACTION = "DeactivateScoreBug_%d";

  private final MediaLiveAsyncClient client;

  public MediaLiveOverlay(MediaLiveAsyncClient client) {
    this.client = client;
  }

  public void activateOverlay(String channelId, String overlayURL, Long appInstanceId) {
    LOG.info("Activating graphic overlay in MediaLive channel={}", channelId);
    var batchUpdateScheduleRequest =
        BatchUpdateScheduleRequest.builder()
            .channelId(channelId)
            .creates(
                builder ->
                    builder
                        .scheduleActions(
                            builder1 ->
                                builder1
                                    .actionName(ACTIVATE_ACTION.formatted(appInstanceId))
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
          "Error activating graphic overlay to MediaLive channel={} due to {}",
          channelId,
          ExceptionUtils.getRootCauseMessage(e));
    }
  }

  public void deactivateAndDeleteOverlay(String channelId, Long appInstanceId) {
    LOG.info("Deactivating graphic overlay for MediaLive channel={}", channelId);
    var batchUpdateScheduleRequestForDeactivate =
        BatchUpdateScheduleRequest.builder()
            .channelId(channelId)
            .creates(
                builder ->
                    builder
                        .scheduleActions(
                            scheduleBuilder ->
                                scheduleBuilder
                                    .actionName(DEACTIVATE_ACTION.formatted(appInstanceId))
                                    .scheduleActionStartSettings(
                                        actionStartBuilder ->
                                            actionStartBuilder.immediateModeScheduleActionStartSettings(SdkBuilder::build))
                                    .scheduleActionSettings(
                                        actionSettingsBuilder ->
                                            actionSettingsBuilder.motionGraphicsImageDeactivateSettings(SdkBuilder::build))
                                    .build())
                        .build())

            .build();

    var batchUpdateScheduleRequestForDelete =
        BatchUpdateScheduleRequest.builder()
            .channelId(channelId)
            .deletes(
                builder -> builder.actionNames(ACTIVATE_ACTION.formatted(appInstanceId), DEACTIVATE_ACTION.formatted(appInstanceId))
            )
            .build();

    try {
      client.batchUpdateSchedule(batchUpdateScheduleRequestForDeactivate).join();
      client.batchUpdateSchedule(batchUpdateScheduleRequestForDelete).join();
    } catch (Exception e) {
      LOG.warn(
          "Error deactivating or deleting graphic overlay from MediaLive channel={} due to {}",
          channelId,
          ExceptionUtils.getRootCauseMessage(e));
    }
  }
}
