package ink.magma.zthpatches.bukkit.patches;

public abstract class PatchAddon {
    private boolean isEnabled = false;

    public abstract void onEnable();

    public abstract void onDisable();

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
