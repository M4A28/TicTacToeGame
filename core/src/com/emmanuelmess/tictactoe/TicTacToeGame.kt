package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FillViewport

const val DEBUG = true

class TicTacToeGame : ApplicationAdapter() {
    object Size {
        const val WIDTH = 1000f
        const val HEIGHT = 16 * WIDTH / 9

        const val C = 10f
    }

    object Grid {
        const val STROKE_WIDTH = 2f * Size.C
        const val TOP = 45f * Size.C
        const val HEIGHT = 90f * Size.C
        const val LEFT = 15f * Size.C
        const val WIDTH = 70f * Size.C
    }

    private lateinit var ticTacToeText: TicTacToeText
    private lateinit var ticTacToeRenderer: TicTacToeRenderer
    private lateinit var xoText: XOText

    private lateinit var viewport: FillViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer

    override fun create() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        ticTacToeText = TicTacToeText(generator)
        xoText = XOText(generator)
        generator.dispose()

        camera = OrthographicCamera().apply {
            setToOrtho(true, Size.WIDTH, Size.HEIGHT)
        }
        viewport = FillViewport(Size.WIDTH, Size.HEIGHT, camera)

        shapeRenderer = ShapeRenderer()

        ticTacToeRenderer = TicTacToeRenderer(
                Grid.STROKE_WIDTH,
                viewport,
                Grid.LEFT,
                Grid.TOP,
                Grid.WIDTH,
                Grid.HEIGHT
        )

        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
    }

    override fun resize(width: Int, height: Int) {
        ticTacToeText.update(width, height)
        viewport.update(width, height, true)
        xoText.update(width, height)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT
                or if(Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)

        shapeRenderer.projectionMatrix = viewport.camera.combined

        ticTacToeText.draw()
        ticTacToeRenderer.draw(shapeRenderer)
        xoText.draw()

        if(DEBUG) {
            ticTacToeText.drawDebug()
            ticTacToeRenderer.drawDebug(shapeRenderer)
            xoText.drawDebug()
        }
    }

    override fun dispose() {
        ticTacToeText.dispose()
        ticTacToeRenderer.dispose()
        shapeRenderer.dispose()
        xoText.dispose()
    }
}