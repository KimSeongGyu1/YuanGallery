package yuan.gallery.gallery.blog.exception;

public class FailToReadFeedException extends RuntimeException {

    public FailToReadFeedException() {
        super("피드 읽기에 실패했습니다");
    }
}
