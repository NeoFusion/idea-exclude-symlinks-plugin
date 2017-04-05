import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.actions.MarkRootActionBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.vfs.VFileProperty;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;

public class MarkSymlinksAsExcludedAction extends MarkRootActionBase {
    public MarkSymlinksAsExcludedAction() {
        super(null, null, AllIcons.Modules.ExcludeRoot);
    }

    @Override
    protected void modifyRoots(@NotNull VirtualFile vFile, @NotNull ContentEntry entry) {
        VfsUtilCore.visitChildrenRecursively(vFile, new VirtualFileVisitor() {
            @Override
            public boolean visitFile(@NotNull VirtualFile file) {
                if (file.isDirectory() && file.is(VFileProperty.SYMLINK)) {
                    entry.addExcludeFolder(file);
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    @Override
    protected boolean isEnabled(@NotNull RootsSelection selection, @NotNull Module module) {
        return true;
    }
}
