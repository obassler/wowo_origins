package io.github.apace100.origins.badge;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.util.KeyBindingUtil;
import io.github.apace100.origins.util.PowerKeyManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public record KeybindBadge(Identifier spriteId, String text) implements Badge {

    public KeybindBadge(SerializableData.Instance instance) {
        this(instance.getId("sprite"), instance.get("text"));
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    public static void addLines(List<TooltipComponent> tooltips, Text text, TextRenderer textRenderer, int widthLimit) {
        if(textRenderer.getWidth(text) > widthLimit) {
            for(OrderedText orderedText : textRenderer.wrapLines(text, widthLimit)) {
                tooltips.add(new OrderedTextTooltipComponent(orderedText));
            }
        } else {
            tooltips.add(new OrderedTextTooltipComponent(text.asOrderedText()));
        }
    }

    @Override
    public List<TooltipComponent> getTooltipComponents(PowerType<?> powerType, int widthLimit, float time, TextRenderer textRenderer) {

        String keyId = PowerKeyManager.getKeyIdentifier(powerType.getIdentifier());

        Text keyName = KeyBindingUtil.getLocalizedName(keyId);
        Text keyText = Text.literal("[").append(keyName).append("]");

        List<TooltipComponent> tooltips = new LinkedList<>();
        addLines(tooltips, Text.translatable(text, keyText), textRenderer, widthLimit);

        return tooltips;

    }

    @Override
    public SerializableData.Instance toData(SerializableData.Instance instance) {
        instance.set("sprite", spriteId);
        instance.set("text", text);
        return instance;
    }

    @Override
    public BadgeFactory getBadgeFactory() {
        return BadgeFactories.KEYBIND;
    }

}
