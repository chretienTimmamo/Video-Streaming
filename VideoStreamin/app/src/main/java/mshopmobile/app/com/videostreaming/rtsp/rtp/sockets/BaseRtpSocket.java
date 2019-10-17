package mshopmobile.app.com.videostreaming.rtsp.rtp.sockets;

import  mshopmobile.app.com.videostreaming.rtsp.rtp.sockets.RtpSocketTcp;
import  mshopmobile.app.com.videostreaming.rtsp.rtp.sockets.RtpSocketUdp;
import  mshopmobile.app.com.videostreaming.rtsp.rtsp.Protocol;
import  mshopmobile.app.com.videostreaming.rtsp.rtsp.RtpFrame;
import  mshopmobile.app.com.videostreaming.rtsp.utils.ConnectCheckerRtsp;
import java.io.OutputStream;

/**
 * Created by pedro on 7/11/18.
 */

public abstract class BaseRtpSocket {

  protected final static String TAG = "BaseRtpSocket";
  protected ConnectCheckerRtsp connectCheckerRtsp;

  BaseRtpSocket(ConnectCheckerRtsp connectCheckerRtsp) {
    this.connectCheckerRtsp = connectCheckerRtsp;
  }

  public static BaseRtpSocket getInstance(ConnectCheckerRtsp connectCheckerRtsp,
      Protocol protocol) {
    return protocol == Protocol.TCP ? new RtpSocketTcp(connectCheckerRtsp) : new RtpSocketUdp(connectCheckerRtsp);
  }

  public abstract void setDataStream(OutputStream outputStream, String host);

  public abstract void sendFrame(RtpFrame rtpFrame);

  public abstract void close();
}
