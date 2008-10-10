package talkie.server.data;

public interface UserStatusListener
{
    void fireStatusChange(
        User user );
}
