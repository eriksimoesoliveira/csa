import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align

class InformationBox(skin: Skin) : Group() {
    private val shapeRenderer = ShapeRenderer()
    private val infoLabel: Label

    init {
        setSize(300f, 500f)

        infoLabel = Label("", skin, "Roboto-Bold-28-White")
        infoLabel.setSize(280f, 480f)
        infoLabel.setPosition(10f, 10f)
        infoLabel.setAlignment(Align.topLeft)
        addActor(infoLabel)

        isVisible = false // Initially hidden
    }

    fun setText(text: String) {
        infoLabel.setText(text)
        infoLabel.wrap = true
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.end()

        shapeRenderer.projectionMatrix = stage.camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.BLACK
        shapeRenderer.rect(x, y, width, height)
        shapeRenderer.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.WHITE
        shapeRenderer.rect(x, y, width, height)
        shapeRenderer.end()

        batch?.begin()
        super.draw(batch, parentAlpha)
    }
}