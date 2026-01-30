package berries.mods.tcwm.gui.widget;

import berries.mods.tcwm.gui.FlueroUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
//? >= 1.21.9
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class PropertiesList {
    public List<Item<AbstractWidget>> items;
    public List<Item<StackPanel>> stackPanels;

    protected PropertiesList() {}

    @SuppressWarnings("all")
    public static PropertiesList newList(int x, int y, int width, int height, List<ItemProperties<?>> properties) {
        var instance = new PropertiesList();
        instance.items = new ArrayList<>();
        instance.stackPanels = new ArrayList<>();
        if (properties.size() * 30 > height) {
            //add scroll...
        }
        for (int i = 0; i < properties.size(); i++) {
            ItemProperties<?> prop = properties.get(i);
            if (prop.widget instanceof AbstractWidget) {
                Item<AbstractWidget> item = new Item<>(x, y + (30 * i), width, 30, prop.label);
                prop.setPosition(item.getX() + width - ((AbstractWidget) prop.widget).getWidth() - 10, item.getY() + ((AbstractWidget) prop.widget).getHeight() - 8);
                item.widget = (AbstractWidget) prop.widget;
                instance.items.add(item);
            } else if (prop.widget instanceof StackPanel) {
                Item<StackPanel> item = new Item<>(x, y + (30 * i), width, 30, prop.label);
                prop.setPosition(item.getX() + width - ((StackPanel) prop.widget).width - 10, item.getY() + ((StackPanel) prop.widget).height - 8);
                item.widget = (StackPanel) prop.widget;
                instance.stackPanels.add(item);
            }
        }
        return instance;
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry>
    void reload(Function<T, T> addFunction, Consumer<T> removeFunction, Function<T, Boolean> containsFunction) {
        for(Item<?> item : items) {
            if (item instanceof Item<?>) {
                if (containsFunction.apply((T) item.widget)) {
                    removeFunction.accept((T) item.widget);
                }
                addFunction.apply((T) item.widget);
            }
        }

        for(Item<StackPanel> item : stackPanels) {
            ((StackPanel) item.widget).reload(addFunction, removeFunction);
        }
    }

    public static class ItemProperties<T> {
        public Component label;
        public T widget;

        private ItemProperties() {}

        public static <T extends GuiEventListener & Renderable & NarratableEntry> ItemProperties<T>
        newInstance(Component label, T widget) {
            ItemProperties<T> instance = new ItemProperties<T>();
            instance.label = label;
            instance.widget = widget;
            return instance;
        }

        public static ItemProperties<StackPanel>
        newInstance(Component label, StackPanel widget) {
            ItemProperties<StackPanel> instance = new ItemProperties<>();
            instance.label = label;
            instance.widget = widget;
            return instance;
        }

        public void setPosition(int x, int y) {
            if (widget instanceof AbstractWidget) {
                ((AbstractWidget) widget).setPosition(x, y);
            } else if (widget instanceof StackPanel) {
                ((StackPanel) widget).setPosition(x, y);
            }
        }
    }

    public static class Item<T> extends AbstractWidget {
        public T widget;

        public Item(int x, int y, int width, int height, Component message) {
            super(x, y, width, height, message);
        }

        //? < 1.21.9 {

        /*@Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
            return false;
        }
        *///? } else {
        @Override
        public boolean mouseClicked(MouseButtonEvent e, boolean b) {
            return false;
        }

        @Override
        public boolean mouseReleased(MouseButtonEvent e) {
            return false;
        }

        @Override
        public boolean mouseDragged(MouseButtonEvent e, double d, double g) {
            return false;
        }
        //? }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            guiGraphics.drawString(minecraft.font, this.getMessage(), getX() + 10, getY() + height / 2, FlueroUI.textColor(0xFFFFFF));
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }
    }

    public static class StackPanel {
        public static final int PADDING = 2;
        protected final List<AbstractWidget> widgets;
        protected final int width;
        protected final int height;

        public StackPanel(int height, AbstractWidget... widgets) {
            this.widgets = new ArrayList<>(List.of(widgets));
            this.width = setPosition(0, 0);
            this.height = height;
        }

        public int setPosition(int x, int y) {
            int totalWidth = x;
            for (int i = 0; i < widgets.size(); i++) {
                AbstractWidget widget = widgets.get(i);
                widget.setX(totalWidth);
                widget.setY(y);
                totalWidth += widget.getWidth() + (i == widgets.size() - 1 ? 0 : PADDING);
            }
            return totalWidth;
        }

        public <T extends GuiEventListener & Renderable & NarratableEntry>
        void reload(Function<T, T> addFunction, Consumer<T> removeFunction) {
            for (AbstractWidget widget : widgets) {
                removeFunction.accept((T)widget);
                addFunction.apply((T)widget);
            }
        }
    }
}
