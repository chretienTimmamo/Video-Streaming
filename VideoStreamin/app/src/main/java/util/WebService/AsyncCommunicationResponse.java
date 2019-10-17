package util.WebService;

public interface AsyncCommunicationResponse {
    void processFinish(int output, Object object, int serviceId);
}
