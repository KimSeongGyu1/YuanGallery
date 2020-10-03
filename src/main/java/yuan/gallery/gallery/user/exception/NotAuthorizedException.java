package yuan.gallery.gallery.user.exception;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super("권한이 없는 유저입니다");
    }
}
