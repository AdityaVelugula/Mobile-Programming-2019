package co.chatsdk.firestore;

import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.events.NetworkEvent;
import co.chatsdk.core.handlers.TypingIndicatorHandler;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import io.reactivex.Completable;
import sdk.chat.micro.MicroChatSDK;
import sdk.chat.micro.rx.DisposableList;
import sdk.chat.micro.types.TypingStateType;

public class FirestoreTypingIndicatorHandler implements TypingIndicatorHandler {

    private DisposableList disposableList = new DisposableList();

    public FirestoreTypingIndicatorHandler () {
        disposableList.add(MicroChatSDK.shared().typingStateStream.subscribe(typingState -> {
            // Get the sender
            String senderId = typingState.fromId;

            if (!senderId.equals(ChatSDK.currentUserID())) {
                disposableList.add(UserHelper.fetchUser(senderId).subscribe((user, throwable) -> {
                    if (throwable == null) {
                        Thread thread = ChatSDK.db().fetchThreadWithEntityID(senderId);
                        if (thread != null) {
                            NetworkEvent networkEvent = NetworkEvent.typingStateChanged(null, thread);
                            if (typingState.getBodyType().equals(TypingStateType.typing())) {
                                networkEvent = NetworkEvent.typingStateChanged(user.getName(), thread);
                            }
                            ChatSDK.events().source().onNext(networkEvent);
                        }
                    }
                }));
            }

        }));
    }

    @Override
    public void typingOn(Thread thread) {
    }

    @Override
    public void typingOff(Thread thread) {

    }

    @Override
    public Completable setChatState(State state, Thread thread) {
        if (!thread.typeIs(ThreadType.Private1to1)) {
            return Completable.error(new Throwable("Only 1 to 1 threads are currently supported"));
        }

        User otherUser = thread.otherUser();
        TypingStateType typingStateType = TypingStateType.none();
        if (state == State.composing) {
            typingStateType = TypingStateType.typing();
        }

        return MicroChatSDK.shared().sendTypingIndicator(otherUser.getEntityID(), typingStateType).ignoreElement();
    }
}
