package aurelienribon.bodyeditor.canvas;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.utils.gdx.SpriteUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Label {
    static {
        Tween.registerAccessor(Label.class, new Accessor());
    }

    public enum Anchor {TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT}

    private enum State {SHOWN, HIDDEN, HIDDEN_SEMI}

    private String text;
    private Sprite icon;
    private TouchCallback callback;
    private final BitmapFont font;
    private final Color color;
    private final Anchor anchor;
    private final GlyphLayout layout = new GlyphLayout();

    private final TweenManager tweenManager = new TweenManager();
    private final Sprite bg;
    private final float y, w, h;
    private float offsetX;
    private boolean isTouchOver = false;
    private State state = State.HIDDEN;

    public Label(float y, float w, float h, String text, BitmapFont font, Color color, Anchor anchor) {
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.font = font;
        this.color = color;
        this.anchor = anchor;

        this.bg = new Sprite(Assets.inst().get("data/white.png", Texture.class));
        bg.setSize(w * 11 / 10, h);
        bg.setColor(color);

        offsetX = -w;
    }

    // -------------------------------------------------------------------------
    // Callback
    // -------------------------------------------------------------------------

    public interface TouchCallback {
        void touchDown(Label source);

        void touchEnter(Label source);

        void touchExit(Label source);
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(String path) {
        this.icon = new Sprite(Assets.inst().get(path, Texture.class));
    }

    public void setCallback(TouchCallback callback) {
        this.callback = callback;
    }

    public void hide() {
        if (state == State.HIDDEN) return;
        tweenManager.killTarget(this);
        Tween.to(this, Accessor.OFFSET_X, 0.3f).target(-w).start(tweenManager);
        Tween.to(this, Accessor.ALPHA, 0.3f).target(color.a).start(tweenManager);
        isTouchOver = false;
        state = State.HIDDEN;
    }

    public void hideSemi() {
        if (state == State.HIDDEN_SEMI) return;
        tweenManager.killTarget(this);
        Tween.to(this, Accessor.OFFSET_X, 0.3f).target(w / 10 - w).start(tweenManager);
        Tween.to(this, Accessor.ALPHA, 0.3f).target(color.a).start(tweenManager);
        isTouchOver = false;
        state = State.HIDDEN_SEMI;
    }

    public void show() {
        if (state == State.SHOWN) return;
        tweenManager.killTarget(this);
        Tween.to(this, Accessor.OFFSET_X, 0.3f).target(0).start(tweenManager);
        Tween.to(this, Accessor.ALPHA, 0.3f).target(color.a).start(tweenManager);
        state = State.SHOWN;
    }

    public void tiltOn() {
        float tx;

        switch (state) {
            case SHOWN:
                tx = w / 10;
                break;
            case HIDDEN_SEMI:
                tx = -w + w / 10 + w / 10;
                break;
            default:
                return;
        }

        tweenManager.killTarget(this);
        Tween.to(this, Accessor.ALPHA, 0.2f).target(1).start(tweenManager);
        Tween.to(this, Accessor.OFFSET_X, 0.2f).target(tx).start(tweenManager);
    }

    public void tiltOff() {
        float tx;

        switch (state) {
            case SHOWN:
                tx = 0;
                break;
            case HIDDEN_SEMI:
                tx = -w + w / 10;
                break;
            default:
                return;
        }

        tweenManager.killTarget(this);
        Tween.to(this, Accessor.ALPHA, 0.2f).target(color.a).start(tweenManager);
        Tween.to(this, Accessor.OFFSET_X, 0.2f).target(tx).start(tweenManager);
    }

    public void draw(SpriteBatch batch) {
        tweenManager.update(Gdx.graphics.getDeltaTime());

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();
        float x = isAnchorLeft() ? offsetX : sw - w - offsetX;
        float bgX = isAnchorLeft() ? x - w / 10 : x;

        layout.setText(font, text);
        float width = layout.width;// contains the width of the current set text
        float textH = layout.height; // contains the height of the current set text

        bg.setPosition(bgX, sh - y);
        bg.draw(batch);

        if (icon != null) {
            icon.setPosition(x + 10, sh - y + h / 2 - icon.getHeight() / 2);
            icon.draw(batch);
            font.setColor(Color.WHITE);
            font.draw(batch, text, x + 10 + icon.getWidth() + 10, sh - y + h / 2 + textH / 2);
        } else {
            font.setColor(Color.WHITE);
            font.draw(batch, text, x + 10, sh - y + h / 2 + textH / 2);
        }
    }

    public boolean touchMoved(int x, int y) {
        y = Gdx.graphics.getHeight() - y - 1;
        if (isOver(x, y) && !isTouchOver && state == State.SHOWN) {
            isTouchOver = true;
            tiltOn();
            if (callback != null) callback.touchEnter(this);
        } else if (!isOver(x, y) && isTouchOver) {
            isTouchOver = false;
            tiltOff();
            if (callback != null) callback.touchExit(this);
        }
        return isOver(x, y);
    }

    public boolean touchDown(int x, int y) {
        y = Gdx.graphics.getHeight() - y - 1;
        if (isOver(x, y) && callback != null) callback.touchDown(this);
        return isOver(x, y);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private boolean isOver(float x, float y) {
        return SpriteUtils.isOver(bg, x, y);
    }

    private boolean isAnchorLeft() {
        return anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.TOP_LEFT;
    }

    // -------------------------------------------------------------------------
    // Tween Accessor
    // -------------------------------------------------------------------------

    private static class Accessor implements TweenAccessor<Label> {
        public static final int OFFSET_X = 1;
        public static final int ALPHA = 2;

        @Override
        public int getValues(Label target, int tweenType, float[] returnValues) {
            switch (tweenType) {
                case OFFSET_X:
                    returnValues[0] = target.offsetX;
                    return 1;
                case ALPHA:
                    returnValues[0] = target.bg.getColor().a;
                    return 1;
                default:
                    assert false;
                    return -1;
            }
        }

        @Override
        public void setValues(Label target, int tweenType, float[] newValues) {
            switch (tweenType) {
                case OFFSET_X:
                    target.offsetX = newValues[0];
                    break;
                case ALPHA:
                    Color c = target.bg.getColor();
                    target.bg.setColor(c.r, c.g, c.b, newValues[0]);
                    break;
                default:
                    assert false;
            }
        }
    }

}
