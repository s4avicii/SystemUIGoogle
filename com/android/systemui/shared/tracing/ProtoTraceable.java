package com.android.systemui.shared.tracing;

import com.android.systemui.tracing.nano.SystemUiTraceProto;

public interface ProtoTraceable<T> {
    void writeToProto(SystemUiTraceProto systemUiTraceProto);
}
