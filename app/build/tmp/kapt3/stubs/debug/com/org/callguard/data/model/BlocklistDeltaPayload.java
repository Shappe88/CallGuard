package com.org.callguard.data.model;

/**
 * Shape of a delta update payload (GET /blocklist/delta?since=N).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B{\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\t\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0\tH\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003J\u0015\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0010H\u00c6\u0003J\u0087\u0001\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\t2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0010H\u00c6\u0001J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020-H\u00d6\u0001J\t\u0010.\u001a\u00020\u0006H\u00d6\u0001R\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0016\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\"\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018\u00a8\u0006/"}, d2 = {"Lcom/org/callguard/data/model/BlocklistDeltaPayload;", "", "from", "", "to", "signature", "", "checksum", "addedBlocked", "", "Lcom/org/callguard/data/model/BlockedNumberDto;", "removedBlocked", "addedAllowlisted", "Lcom/org/callguard/data/model/AllowlistedNumberDto;", "removedAllowlisted", "policy", "", "(JJLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "getAddedAllowlisted", "()Ljava/util/List;", "getAddedBlocked", "getChecksum", "()Ljava/lang/String;", "getFrom", "()J", "getPolicy", "()Ljava/util/Map;", "getRemovedAllowlisted", "getRemovedBlocked", "getSignature", "getTo", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class BlocklistDeltaPayload {
    @com.google.gson.annotations.SerializedName(value = "from")
    private final long from = 0L;
    @com.google.gson.annotations.SerializedName(value = "to")
    private final long to = 0L;
    @com.google.gson.annotations.SerializedName(value = "signature")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String signature = null;
    @com.google.gson.annotations.SerializedName(value = "checksum")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String checksum = null;
    @com.google.gson.annotations.SerializedName(value = "added_blocked")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.org.callguard.data.model.BlockedNumberDto> addedBlocked = null;
    @com.google.gson.annotations.SerializedName(value = "removed_blocked")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> removedBlocked = null;
    @com.google.gson.annotations.SerializedName(value = "added_allowlisted")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> addedAllowlisted = null;
    @com.google.gson.annotations.SerializedName(value = "removed_allowlisted")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> removedAllowlisted = null;
    @com.google.gson.annotations.SerializedName(value = "policy")
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> policy = null;
    
    public BlocklistDeltaPayload(long from, long to, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.BlockedNumberDto> addedBlocked, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> removedBlocked, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> addedAllowlisted, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> removedAllowlisted, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> policy) {
        super();
    }
    
    public final long getFrom() {
        return 0L;
    }
    
    public final long getTo() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSignature() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChecksum() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.org.callguard.data.model.BlockedNumberDto> getAddedBlocked() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRemovedBlocked() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> getAddedAllowlisted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRemovedAllowlisted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getPolicy() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.org.callguard.data.model.BlockedNumberDto> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.org.callguard.data.model.BlocklistDeltaPayload copy(long from, long to, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.BlockedNumberDto> addedBlocked, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> removedBlocked, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> addedAllowlisted, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> removedAllowlisted, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> policy) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}