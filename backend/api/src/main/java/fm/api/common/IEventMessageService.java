package fm.api.common;

public interface IEventMessageService {

    /**
     * Sends an event to the topic event.message.[some identifier]
     *
     * @param event Message event
     */
    void sendEvent(EventMessageDTO event);
}
