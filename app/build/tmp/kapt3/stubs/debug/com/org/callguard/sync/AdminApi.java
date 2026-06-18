package com.org.callguard.sync;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\bJ(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ$\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0007\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0016"}, d2 = {"Lcom/org/callguard/sync/AdminApi;", "", "addNumber", "Lretrofit2/Response;", "Lcom/org/callguard/sync/ContactEntry;", "auth", "", "req", "(Ljava/lang/String;Lcom/org/callguard/sync/ContactEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNumber", "Ljava/lang/Void;", "id", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNumbers", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/org/callguard/sync/LoginResponse;", "Lcom/org/callguard/sync/LoginRequest;", "(Lcom/org/callguard/sync/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "app_debug"})
public abstract interface AdminApi {
    
    @retrofit2.http.POST(value = "api/v1/admin/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.LoginRequest req, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.sync.LoginResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/admin/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.LoginRequest req, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.sync.LoginResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/admin/numbers")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNumbers(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String auth, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.org.callguard.sync.ContactEntry>>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/admin/numbers")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addNumber(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String auth, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.ContactEntry req, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.org.callguard.sync.ContactEntry>> $completion);
    
    @retrofit2.http.DELETE(value = "api/v1/admin/numbers/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteNumber(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String auth, @retrofit2.http.Path(value = "id")
    int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.lang.Void>> $completion);
}