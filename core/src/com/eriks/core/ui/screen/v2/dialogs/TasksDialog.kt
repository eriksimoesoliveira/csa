package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.GameController
import com.eriks.core.TaskController
import com.eriks.core.objects.Family
import com.eriks.core.objects.tasks.ProgressionTaskEnum
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class TasksDialog: FullScreenDialog(false) {

    private val scrollTableFill = Table()
    private val mainTable = Table()

    override fun show(stage: Stage?): Dialog {

        val template = ImageCache.getImage("ui/tasks-bg.png")
        addActor(template)
        UIUtil.centerInScreen(template)

        buildTable()


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


        return super.show(stage)
    }

    private fun buildTable() {
        scrollTableFill.clear()
        mainTable.clear()

        val progressiveTitle = Label("Progressive Tasks", UIController.skin, "Roboto-Bold-45")

//        mainTable.debug()
        mainTable.top()
        mainTable.add(progressiveTitle).colspan(3).left()
        mainTable.row()

        TaskController.progressionTasks.forEach {
            mainTable.add(CheckBox(null, UIController.skin).apply {
                width = 33f
                height = 33f
                isDisabled = true
            }).padLeft(10f).width(35f).left()
            mainTable.add(Label("${it.description}", UIController.skin, "Roboto-Bold-28-White")).padLeft(5f).left().width(620f)
            val buttonLabel = if (it.isCompleted) {
                TextButton("$${it.rewardValue}", UIController.skin, "claim-button").apply {
                    addListener(object : ClickListener() {
                        override fun clicked(event: InputEvent?, x: Float, y: Float) {
                            TaskController.claimTaskProgression(it)
                            buildTable()
                        }
                    })
                }
            } else {
                val progression = when (it.taskEnum) {
                    ProgressionTaskEnum.PACKS_OPEN_TASK -> GameController.getTotalOpenPacks().toString()
                    ProgressionTaskEnum.ALBUM_VALUE_TASK -> GameController.albumValueFormatted
                    ProgressionTaskEnum.CARDS_PLACED_TASK -> GameController.getTotalCardsPlaced().toString()
                    ProgressionTaskEnum.CARDS_SOLD_TASK -> GameController.getTotalCardsSold().toString()
                }
                TextButton(progression, UIController.skin, "claimed-button")
            }
            buttonLabel.width = 100f
//            buttonLabel.setAlignment(Align.center)
//            buttonLabel.align = Align.center
            mainTable.add(buttonLabel).right().width(100f)
            mainTable.row()
        }

        Family.values().forEach { collection ->
            val title = Label(collection.friendlyName, UIController.skin, "Roboto-Bold-45")
            mainTable.add(title).colspan(3).left().padTop(20f)
            mainTable.row()

            TaskController.collectionTasks.filter { it.collectionTaskEnum.family == collection }.forEach { task ->
                mainTable.add(CheckBox(null, UIController.skin).apply {
                    isChecked = task.isCompleted
                    width = 33f
                    height = 33f
                    isDisabled = true
                }).padLeft(10f).width(35f).left()
                mainTable.add(Label("${task.collectionTaskEnum.description}", UIController.skin, "Roboto-Bold-28-White")).padLeft(5f).left().width(620f)
                val buttonLabel = if (!task.isCompleted) {
                    TextButton(TaskController.getCollectionTaskProgressionFormatted(task.collectionTaskEnum.family, task.collectionTaskEnum.weaponFloat), UIController.skin, "claimed-button")
                } else if (task.isCompleted && !task.isClaimed) {
                    TextButton("$${task.collectionTaskEnum.reward}", UIController.skin, "claim-button").apply {
                        addListener(object : ClickListener() {
                            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                                TaskController.claimTaskCollection(task)
                                buildTable()
                            }
                        })
                    }
                } else {
                    TextButton("Claimed", UIController.skin, "claimed-button")
                }
                buttonLabel.width = 100f
//                buttonLabel.setAlignment(Align.center)
                mainTable.add(buttonLabel).right().width(100f)
                mainTable.row()
            }
        }

        mainTable.pack()

        scrollTableFill.padTop(15f)
        scrollTableFill.padLeft(15f)
        scrollTableFill.top().left()
        scrollTableFill.add(mainTable).row()
    }


}