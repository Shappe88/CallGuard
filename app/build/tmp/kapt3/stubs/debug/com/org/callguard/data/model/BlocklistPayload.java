package com.org.callguard.data.model;

/**
 * Shape of the seed blocklist (assets/seed_blocklist.json) and the
 * full-refresh OTA payload (GET /blocklist/full). Both use this structure
 * so the seed loader and sync engine can share parsing/apply logic.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\tH\u00c6\u0003J\u0015\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000eH\u00c6\u0003Jg\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000eH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006)"}, d2 = {"Lcom/org/callguard/data/model/BlocklistPayload;", "", "version", "", "releasedAt", "", "signature", "checksum", "blockedNumbers", "", "Lcom/org/callguard/data/model/BlockedNumberDto;", "allowlistedNumbers", "Lcom/org/callguard/data/model/AllowlistedNumberDto;", "policy", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "getAllowlistedNumbers", "()Ljava/util/List;", "getBlockedNumbers", "getChecksum", "()Ljava/lang/String;", "getPolicy", "()Ljava/util/Map;", "getReleasedAt", "getSignature", "getVersion", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class BlocklistPayload {
    @com.google.gson.annotations.SerializedName(value = "version")
    private final long version = 0L;
    @com.google.gson.annotations.SerializedName(value = "released_at")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String releasedAt = null;
    @com.google.gson.annotations.SerializedName(value = "signature")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String signature = null;
    @com.google.gson.annotations.SerializedName(value = "checksum")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String checksum = null;
    @com.google.gson.annotations.SerializedName(value = "blocked_numbers")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.org.callguard.data.model.BlockedNumberDto> blockedNumbers = null;
    @com.google.gson.annotations.SerializedName(value = "allowlisted_numbers")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> allowlistedNumbers = null;
    @com.google.gson.annotations.SerializedName(value = "policy")
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> policy = null;
    
    public BlocklistPayload(long version, @org.jetbrains.annotations.NotNull()
    java.lang.String releasedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.BlockedNumberDto> blockedNumbers, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> allowlistedNumbers, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> policy) {
        super();
    }
    
    public final long getVersion() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReleasedAt() {
        return null;
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
    public final java.util.List<com.org.callguard.data.model.BlockedNumberDto> getBlockedNumbers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> getAllowlistedNumbers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getPolicy() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
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
    public final java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.org.callguard.data.model.BlocklistPayload copy(long version, @org.jetbrains.annotations.NotNull()
    java.lang.String releasedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.BlockedNumberDto> blockedNumbers, @org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.model.AllowlistedNumberDto> allowlistedNumbers, @org.jetbrains.annotations.NotNull()
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