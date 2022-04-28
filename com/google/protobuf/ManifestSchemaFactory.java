package com.google.protobuf;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.nio.charset.Charset;

public final class ManifestSchemaFactory {
    public static final C24861 EMPTY_FACTORY = new MessageInfoFactory() {
        public final boolean isSupported(Class<?> cls) {
            return false;
        }

        public final MessageInfo messageInfoFor(Class<?> cls) {
            throw new IllegalStateException("This should never be called.");
        }
    };
    public final MessageInfoFactory messageInfoFactory;

    public static class CompositeMessageInfoFactory implements MessageInfoFactory {
        public MessageInfoFactory[] factories;

        public final boolean isSupported(Class<?> cls) {
            for (MessageInfoFactory isSupported : this.factories) {
                if (isSupported.isSupported(cls)) {
                    return true;
                }
            }
            return false;
        }

        public final MessageInfo messageInfoFor(Class<?> cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return messageInfoFactory.messageInfoFor(cls);
                }
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No factory is available for message type: ");
            m.append(cls.getName());
            throw new UnsupportedOperationException(m.toString());
        }

        public CompositeMessageInfoFactory(MessageInfoFactory... messageInfoFactoryArr) {
            this.factories = messageInfoFactoryArr;
        }
    }

    public ManifestSchemaFactory() {
        MessageInfoFactory messageInfoFactory2;
        MessageInfoFactory[] messageInfoFactoryArr = new MessageInfoFactory[2];
        messageInfoFactoryArr[0] = GeneratedMessageInfoFactory.instance;
        try {
            messageInfoFactory2 = (MessageInfoFactory) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception unused) {
            messageInfoFactory2 = EMPTY_FACTORY;
        }
        messageInfoFactoryArr[1] = messageInfoFactory2;
        CompositeMessageInfoFactory compositeMessageInfoFactory = new CompositeMessageInfoFactory(messageInfoFactoryArr);
        Charset charset = Internal.UTF_8;
        this.messageInfoFactory = compositeMessageInfoFactory;
    }
}
