package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.eriks.core.GameController
import com.eriks.core.objects.Ranking
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RankingDialog: FullScreenDialog(false) {

    private val scrollTableFill = Table()
    private val mainTable = Table()

    override fun show(stage: Stage?): Dialog {

        val template = ImageCache.getImage("ui/rankingbg.png")
        addActor(template)
        UIUtil.centerInScreen(template)

        CoroutineScope(Dispatchers.Main).launch {
            val ranking = withContext(Dispatchers.IO) {
                GameController.getRanking()
            }
            // Build the table after receiving the ranking
            buildTable(ranking)

            val scrollableView = ScrollPane(scrollTableFill)
            scrollableView.width = 825f
            scrollableView.height = 767f
            scrollableView.x = 548f
            scrollableView.y = 127f
            scrollableView.layout()
            scrollableView.setScrollingDisabled(true, false)
            scrollableView.setScrollbarsOnTop(false)
            scrollableView.setForceScroll(false, false)
            scrollableView.fadeScrollBars = false
            scrollableView.scrollTo(0f, mainTable.height, 0f, 0f)
            addActor(scrollableView)
        }

        return super.show(stage)
    }

    private fun buildTable(ranking: List<Ranking>) {
        scrollTableFill.clear()
        mainTable.clear()

        mainTable.top()
        mainTable.padLeft(50f)
//        mainTable.debug()
        ranking.forEachIndexed { index, ranking ->
            val font = if (ranking.userName == GameController.getNickName()) {
                "Roboto-Bold-38"
            } else {
                "Roboto-Bold-45"
            }
            mainTable.add(Label((index + 1).toString(), UIController.skin, font)).left().padRight(40f).padBottom(10f)
            mainTable.add(Label(ranking.userName, UIController.skin, font)).left().width(400f).padBottom(10f)
            mainTable.add(Label(UIUtil.decimalFormat.format(ranking.value), UIController.skin, font)).right().padLeft(100f).padBottom(10f)
            mainTable.row()
        }


        mainTable.pack()

        scrollTableFill.padTop(15f)
        scrollTableFill.padLeft(15f)
        scrollTableFill.top().left()
        scrollTableFill.add(mainTable).row()
    }

    override fun closeButtonClicked() {

    }
}