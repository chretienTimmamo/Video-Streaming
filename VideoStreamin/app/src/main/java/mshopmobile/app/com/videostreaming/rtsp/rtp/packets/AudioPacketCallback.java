package mshopmobile.app.com.videostreaming.rtsp.rtp.packets;

import mshopmobile.app.com.videostreaming.rtsp.rtsp.RtpFrame;

/**
 * Created by pedro on 7/11/18.
 */

public interface AudioPacketCallback {
  void onAudioFrameCreated(RtpFrame rtpFrame);
}
