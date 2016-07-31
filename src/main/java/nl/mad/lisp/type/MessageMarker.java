package nl.mad.lisp.type;

public class MessageMarker extends SelfishObject {
	public static MessageMarker MESSAGE_MARKER = new MessageMarker();

	public MessageMarker() {
		super(SelfishObject.OBJECT_ROOT, ":");
	}

}
