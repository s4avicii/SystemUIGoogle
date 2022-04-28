package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.util.Objects;

public final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor> {
    public final FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    public final FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(Object obj) {
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        Objects.requireNonNull(extendableMessage);
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet = extendableMessage.extensions;
        Objects.requireNonNull(fieldSet);
        if (fieldSet.isImmutable) {
            extendableMessage.extensions = extendableMessage.extensions.clone();
        }
        return extendableMessage.extensions;
    }

    public final void makeImmutable(Object obj) {
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet = ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
        Objects.requireNonNull(fieldSet);
        if (!fieldSet.isImmutable) {
            fieldSet.fields.makeImmutable();
            fieldSet.isImmutable = true;
        }
    }

    public final boolean hasExtensions(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }
}
