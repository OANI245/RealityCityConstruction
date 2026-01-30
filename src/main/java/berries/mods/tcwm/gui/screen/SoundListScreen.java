package berries.mods.tcwm.gui.screen;

import berries.mods.tcwm.gui.Icons;
import berries.mods.tcwm.gui.widget.Button;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
//? >= 1.21.9 {
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
//? }
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SoundListScreen extends MVScreen {
    // 分页大小
    protected static final int PAGE_SIZE = 100;
    // 组件尺寸
    protected static final int BUTTON_SIZE = 18;
    protected static final int SEARCH_BOX_WIDTH = 200;
    protected static final int SEARCH_BOX_HEIGHT = 15;
    protected static final int HEADER_HEIGHT = 35;
    protected static final int LIST_ENTRY_HEIGHT = 20;
    // 间距常量
    protected static final int SPACING_SMALL = 4;
    protected static final int SPACING_MEDIUM = 8;
    protected final EditSoundPlayerScreen previous;
    protected final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    protected SoundList listWidget;
    protected AbstractButton cancelButton;
    protected EditBox searchBox;
    protected Identifier currentSound;

    public SoundListScreen(EditSoundPlayerScreen previous) {
        super(MVComponent.text("浏览音频"));
        this.previous = previous;
        // 处理空值/非法格式
        String soundId = previous.soundID == null ? "" : previous.soundID.trim();
        try {
            this.currentSound = MVIdentifier.get(soundId);
        } catch (Exception e) {
            // 回退到默认值，避免崩溃
            this.currentSound = MVIdentifier.get("tcwm", "music.example");
        }
    }

    @Override
    public void initScreen() {
        if (minecraft == null) return;

        //this.layout.addTitleHeader(this.getTitle(), minecraft.font);
        var availableSounds = minecraft.getSoundManager().getAvailableSounds().stream();
        //? < 1.20.3 {
        /*this.listWidget = addWidget(new SoundList(availableSounds.toList(), currentSound));
        GridLayout.RowHelper rowHelper = new GridLayout().columnSpacing(SPACING_MEDIUM).createRowHelper(2);
        this.cancelButton = rowHelper.addChild(net.minecraft.client.gui.components.Button.builder(MVComponent.text("返回"), (button) -> {
            minecraft.setScreen(previous);
        }).build());
        rowHelper.getGrid().visitWidgets(this::addRenderableWidget);
        rowHelper.getGrid().setPosition(this.width / 2 - 75, this.height - 28);
        rowHelper.getGrid().arrangeElements();
        this.searchBox = addRenderableWidget(new EditBox(minecraft.font, width / 2 - SEARCH_BOX_WIDTH / 2, minecraft.font.lineHeight + 13, SEARCH_BOX_WIDTH, SEARCH_BOX_HEIGHT, MVComponent.EMPTY));
        searchBox.setResponder((str) -> {
            listWidget.search(searchBox.getValue());
        });
        searchBox.setHint(MVComponent.text("音频 ID..."));
        *///? } else {
        this.listWidget = this.layout.addToContents(new SoundList(availableSounds.toList(), currentSound));
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(SPACING_MEDIUM));
        this.cancelButton = footer.addChild(new Button(0, 0, 100, BUTTON_SIZE, MVComponent.text("返回"), true, (button) -> {
            minecraft.setScreen(previous);
        }));
        this.layout.setHeaderHeight(HEADER_HEIGHT);
        LinearLayout header = this.layout.addToHeader(LinearLayout.vertical().spacing(SPACING_SMALL));
        header.defaultCellSetting().alignHorizontallyCenter();
        Component titleComponent = MVComponent.text("选择音频");
        //? < 1.21.11 {
        /*header.addChild(new StringWidget(minecraft.font.width(titleComponent.getVisualOrderText()), minecraft.font.lineHeight, titleComponent, minecraft.font));
        this.searchBox = header.addChild(new EditBox(minecraft.font, SEARCH_BOX_WIDTH, SEARCH_BOX_HEIGHT, MVComponent.EMPTY));
        *///? } else {
        header.addChild(new StringWidget(titleComponent, this.font));
        this.searchBox = header.addChild(new EditBox(this.font, 0, 0, SEARCH_BOX_WIDTH, SEARCH_BOX_HEIGHT, MVComponent.EMPTY));
        //? }
        searchBox.setResponder((str) -> {
            listWidget.search(searchBox.getValue());
        });
        searchBox.setHint(MVComponent.text("音频 ID..."));
        //? }
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    private <T> T getMinecraftOrNull(Supplier<T> supplier) {
        return minecraft == null ? null : supplier.get();
    }

    private void getMinecraftOrNull(Runnable e) {
        if (minecraft != null) e.run();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        //? >= 1.20.3 {
        if (this.listWidget != null) {
            this.listWidget.updateSize(this.width, this.layout);
        }
        //? }
    }


    public void saveChangeAndBack(Identifier id) {
        previous.soundID = id.toString();
        getMinecraftOrNull(() -> minecraft.setScreen(previous));
    }


    @Override
    public void onClose() {
        getMinecraftOrNull(() -> minecraft.setScreen(previous));
    }

    @Override
    public void renderScreen(GuiGraphics graphics, int mouseX, int mouseY, float f) {
        //? < 1.20.5 {
        /*this.renderBackground(graphics);
        this.listWidget.render(graphics, mouseX, mouseY, f);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 7, 16777215);
        *///? } else if < 1.21.6 {
        /*this.renderBackground(graphics, mouseX, mouseY, f);
        *///? }
        super.renderScreen(graphics, mouseX, mouseY, f);
    }

    public sealed abstract class ListEntry extends ContainerObjectSelectionList.Entry<ListEntry> permits SoundListEntry, PageSelectEntry {
    }

    @SuppressWarnings("all")
    public non-sealed class PageSelectEntry extends ListEntry {
        protected final Consumer<Boolean> onPageChange;
        protected int currentPage;
        protected final int pageCount;

        protected final Button nextPageButton;
        protected final Button previousPageButton;

        public PageSelectEntry(Consumer<Boolean> onPageChange, int currentPage, int pageCount) {
            this.onPageChange = onPageChange;
            this.pageCount = Math.max(1, pageCount); // 至少1页
            this.currentPage = Math.max(1, Math.min(currentPage, this.pageCount));

            nextPageButton = new Button(0, 0, 18, 18, Icons.ARROW_FORWARD, MVComponent.EMPTY, (button) -> {
                this.onPageChange.accept(true);
            });

            previousPageButton = new Button(0, 0, 18, 18, Icons.ARROW_BACK, MVComponent.EMPTY, (button) -> {
                this.onPageChange.accept(false);
            });
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            if (currentPage <= 1) {
                return List.of(nextPageButton);
            } else if (currentPage >= pageCount) {
                return List.of(previousPageButton);
            }
            return List.of(nextPageButton, previousPageButton);
        }

        @Override
        //? < 1.21.9 {
        /*public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) *///? } else {
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovering, float partialTick)
        //? }
        {
            //? >= 1.21.9 {
            int top = getContentY();
            int left = getContentX();
            int width = getContentWidth();
            int height = getContentHeight();
            //? }
            guiGraphics.drawCenteredString(SoundListScreen.this.minecraft.font, MVComponent.text(currentPage + "/" + pageCount), (left * 2 + width) / 2, top + height / 2 - minecraft.font.lineHeight / 2, -1);
            nextPageButton.setX(left + width - 18);
            previousPageButton.setX(left);
            nextPageButton.setY(top);
            previousPageButton.setY(top);
            if (currentPage > 1) {
                previousPageButton.render(guiGraphics, mouseX, mouseY, partialTick);
            }
            if (currentPage < pageCount) {
                nextPageButton.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            if (currentPage <= 1) {
                return List.of(nextPageButton);
            } else if (currentPage >= pageCount) {
                return List.of(previousPageButton);
            }
            return List.of(nextPageButton, previousPageButton);
        }
    }

    @SuppressWarnings("all")
    public non-sealed class SoundListEntry extends ListEntry {
        protected final MutableComponent text;
        protected final Button playButton;
        protected final Button selectButton;
        protected final Identifier id;
        protected boolean selected = false;
        protected boolean playing = false;

        protected SoundListEntry(Identifier id) {
            this.id = id;
            this.text = MVComponent.text(id.toString()).copy();
            // 提取按钮创建：复用逻辑+简化匿名类
            this.playButton = createPlayButton();
            this.selectButton = createSelectButton();
        }

        // 提取播放按钮逻辑
        private Button createPlayButton() {
            Button button = new Button(0, 0, 16, 16, Icons.PLAY_ARROW, MVComponent.EMPTY, false, this::onPlayButtonClick);// 分离点击逻辑，提升可读性
            return button;
        }

        // 提取选择按钮逻辑
        private Button createSelectButton() {
            Button button = new Button(0, 0, 16, 16, Icons.CHECK, MVComponent.EMPTY, false, this::onSelectButtonClick);
            return button;
        }

        // 分离播放按钮点击逻辑
        private void onPlayButtonClick(Button button) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null) return;
            if (!playing) {
                button.setIcon(Icons.PAUSE);
                //? >= 1.20.5 {
                mc.level.playLocalSound(mc.player, SoundEvent.createFixedRangeEvent(id, 16.0f), SoundSource.RECORDS, Integer.MAX_VALUE, 1.0f);
                //? } else {
                /*mc.level.playLocalSound(mc.player.getOnPos(), SoundEvent.createFixedRangeEvent(id, 16.0f), SoundSource.RECORDS, Integer.MAX_VALUE, 1.0f, false);
                *///? }
            } else {
                button.setIcon(Icons.PLAY_ARROW);
                mc.getSoundManager().stop(id, SoundSource.RECORDS);
            }
            playing = !playing;
        }

        // 分离选择按钮点击逻辑
        private void onSelectButtonClick(Button button) {
            saveChangeAndBack(id);
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        protected void renderLabel(GuiGraphics guiGraphics, int x, int y) {
            guiGraphics.drawString(SoundListScreen.this.minecraft.font, selected ? this.text.copy().withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN) : this.text, y, x + 5, -1, false);
        }

        @Override
        //? < 1.21.9 {
        /*public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) *///? } else {
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovering, float partialTick)
        //? }
        {
            //? >= 1.21.9 {
            int top = getContentY();
            int left = getContentX();
            int width = getContentWidth();
            int height = getContentHeight();
            //? }
            this.renderLabel(guiGraphics, top, left);
            playButton.setX(left + width - 35);
            selectButton.setX(left + width - 16);
            playButton.setY(top + 1);
            selectButton.setY(top + 1);
            playButton.render(guiGraphics, mouseX, mouseY, partialTick);
            selectButton.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(playButton, selectButton);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(playButton, selectButton);
        }
    }

    public class SoundList extends ContainerObjectSelectionList<ListEntry> {
        protected final List<Identifier> sounds;
        protected List<Identifier> queriedSounds = new ArrayList<>();
        protected Identifier currentSound;
        protected final int pages;
        protected int queriedPages = 0;
        protected int currentPage = 1;

        public SoundList(List<Identifier> sounds, Identifier selected) {
            //? < 1.20.3 {
            /*super(SoundListScreen.this.minecraft, SoundListScreen.this.width, SoundListScreen.this.height, 43, SoundListScreen.this.height - 32, 24);
            *///? } else {
            super(SoundListScreen.this.minecraft, SoundListScreen.this.width, SoundListScreen.this.layout.getContentHeight(), SoundListScreen.this.layout.getHeaderHeight(), SoundListScreen.LIST_ENTRY_HEIGHT);
            //? }
            this.sounds = new ArrayList<>(sounds);
            this.sounds.sort(Identifier::compareTo);
            this.currentSound = selected;
            int pages = 1;
            if (sounds.size() > PAGE_SIZE) {
                pages = (int)Math.ceil(sounds.size() / (float)PAGE_SIZE);
            }
            this.pages = pages;
            this.addEntries();
        }

        public void search(String query) {
            this.currentPage = 1;
            String trimmedQuery = query == null ? "" : query.trim();
            // 清空旧搜索结果（避免内存泄漏）
            this.queriedSounds.clear();

            if (!trimmedQuery.isEmpty()) {
                // 流式处理：更简洁+支持并行（大数据量时）
                this.queriedSounds = new ArrayList<>(this.sounds.stream()
                        .filter(sound -> sound.toString().contains(trimmedQuery))
                        .toList()); // Java 16+ toList() 更高效（不可变）
                // 计算分页数
                this.queriedPages = (int) Math.ceil((double) this.queriedSounds.size() / 100);
            } else {
                this.queriedPages = 0;
            }

            this.clearEntries();
            this.addEntries();
            //? < 1.21.9 {
            /*layout.visitWidgets(SoundListScreen.this::removeWidget);
            layout.visitWidgets(SoundListScreen.this::addRenderableWidget);
            *///? }
            //? < 1.21.6 {
            /*this.setScrollAmount(0);
            *///? } else {
            this.refreshScrollAmount();
            //? }
            SoundListScreen.this.triggerImmediateNarration(true);
        }


        public void setPage(int page) {
            // 边界校验：避免页码越界
            this.currentPage = Math.max(1, Math.min(page, queriedPages > 0 ? queriedPages : pages));
            this.clearEntries();
            this.addEntries();
            //? < 1.21.9 {
            /*layout.visitWidgets(SoundListScreen.this::removeWidget);
            layout.visitWidgets(SoundListScreen.this::addRenderableWidget);
            *///? }
        }

        private void addEntries() {
            List<Identifier> targetList = queriedSounds.isEmpty() ? sounds : queriedSounds;
            int totalSize = targetList.size();
            // 修正：索引从0开始，计算分页起始/结束位置
            int start = (currentPage - 1) * 100;
            int end = Math.min(start + 100, totalSize);
            // 遍历分页区间（闭区间[start, end)）
            for (int i = start; i < end; i++) {
                Identifier sound = targetList.get(i);
                SoundListEntry entry = new SoundListEntry(sound);
                if (sound.equals(currentSound)) { // 简化：直接用ResourceLocation的equals，避免toString
                    entry.setSelected(true);
                }
                this.addEntry(entry);
            }
            // 分页按钮
            this.addEntry(new PageSelectEntry((isForward) -> {
                setPage(currentPage + (isForward ? 1 : -1));
            }, currentPage, queriedPages > 0 ? queriedPages : pages));
        }
    }
}
