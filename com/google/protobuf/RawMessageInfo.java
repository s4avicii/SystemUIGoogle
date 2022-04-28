package com.google.protobuf;

import android.hardware.google.pixel.vendor.PixelAtoms$ReverseDomainNames;

public final class RawMessageInfo implements MessageInfo {
    public final MessageLite defaultInstance;
    public final int flags;
    public final String info = "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000";
    public final Object[] objects;

    public final ProtoSyntax getSyntax() {
        if ((this.flags & 1) == 1) {
            return ProtoSyntax.PROTO2;
        }
        return ProtoSyntax.PROTO3;
    }

    public final boolean isMessageSetWireFormat() {
        if ((this.flags & 2) == 2) {
            return true;
        }
        return false;
    }

    public RawMessageInfo(PixelAtoms$ReverseDomainNames pixelAtoms$ReverseDomainNames, Object[] objArr) {
        this.defaultInstance = pixelAtoms$ReverseDomainNames;
        this.objects = objArr;
        char charAt = "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000".charAt(0);
        if (charAt < 55296) {
            this.flags = charAt;
            return;
        }
        char c = charAt & 8191;
        int i = 1;
        int i2 = 13;
        while (true) {
            int i3 = i + 1;
            char charAt2 = "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000".charAt(i);
            if (charAt2 >= 55296) {
                c |= (charAt2 & 8191) << i2;
                i2 += 13;
                i = i3;
            } else {
                this.flags = (charAt2 << i2) | c;
                return;
            }
        }
    }

    public final MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }
}
