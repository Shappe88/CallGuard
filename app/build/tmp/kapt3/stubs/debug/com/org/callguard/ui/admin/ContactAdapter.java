package com.org.callguard.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0015B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00020\b2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u0014\u0010\u0013\u001a\u00020\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/org/callguard/ui/admin/ContactAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/org/callguard/ui/admin/ContactAdapter$ViewHolder;", "contacts", "", "Lcom/org/callguard/sync/ContactEntry;", "onDeleteClick", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateData", "newContacts", "ViewHolder", "app_debug"})
public final class ContactAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.org.callguard.ui.admin.ContactAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.org.callguard.sync.ContactEntry> contacts;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.org.callguard.sync.ContactEntry, kotlin.Unit> onDeleteClick = null;
    
    public ContactAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.sync.ContactEntry> contacts, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.org.callguard.sync.ContactEntry, kotlin.Unit> onDeleteClick) {
        super();
    }
    
    public final void updateData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.sync.ContactEntry> newContacts) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.org.callguard.ui.admin.ContactAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.org.callguard.ui.admin.ContactAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/org/callguard/ui/admin/ContactAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/org/callguard/databinding/ItemContactBinding;", "(Lcom/org/callguard/ui/admin/ContactAdapter;Lcom/org/callguard/databinding/ItemContactBinding;)V", "bind", "", "contact", "Lcom/org/callguard/sync/ContactEntry;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.org.callguard.databinding.ItemContactBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.org.callguard.databinding.ItemContactBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.org.callguard.sync.ContactEntry contact) {
        }
    }
}