package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.eriks.core.GameController
import com.eriks.core.TaskController
import com.eriks.core.config.GeneralConfigs
import com.eriks.core.objects.CardPackage
import com.eriks.core.objects.Family
import com.eriks.core.objects.PackageOrigin
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.board.BoardGroup
import com.eriks.core.ui.screen.v2.board.FreeLayoutBoardGroup
import com.eriks.core.ui.screen.v2.board.GridLayoutBoardGroup
import com.eriks.core.ui.screen.v2.dialogs.*
import com.eriks.core.ui.util.CircleButton
import com.eriks.core.ui.util.ColorCache
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class MainScreen: CSAScreen() {

    private var currentCollection = Family.KILOWATT_CASE
    private var currentBoard: BoardGroup = GridLayoutBoardGroup(currentCollection)
    private val boardPlace = Group()
    private val collectionTable = Table()
    private var scrollableCollections = ScrollPane(collectionTable)
    private val handCardsQty = Label(GameController.handCards.size.toString(), UIController.skin, "Roboto-Bold-45")
    private val tasksClaimQty = Label("0", UIController.skin, "Roboto-Bold-45")
    private val openPackageDialog = OpenPackageDialog()
    private val handsCard2Dialog = HandCardsDialog(::closeHandDialogCallBack)
    private val creditsLabel = Label("CR 0.00", UIController.skin, "Roboto-Bold-38")
    private val albumValueLabel = Label("AV 0.00", UIController.skin, "Roboto-Bold-38")
    private val shopDialog = ShopDialog()
    private val tasksDialog = TasksDialog()
    private val rankingDialog = RankingDialog()

    private var isHoveringPackButton = false

    private val fakePackButton = CircleButton("ui/packbutton.png", 150f, 150f, GameController.closedPackagesQty) {}
    private val packButton = CircleButton("ui/packbutton.png", 150f, 150f,null, ::packbuttonCallback)
    private val packButtonRed = CircleButton("ui/packbutton-red.png", 150f, 150f, null, ::redPackbuttonCallback)
    private val packButtonWhite = CircleButton("ui/packbutton-white.png", 150f, 150f, null, ::whitePackbuttonCallback)
    private val shopButton = CircleButton("ui/shoppingbutton.png", 150f, 150f, null) { shopDialog.show(stage) }.apply {
        isVisible = false
    }

    override fun show() {
        //Background
        val img = ImageCache.getImage("ui/Main-Background.png")
        UIUtil.centerInScreen(img)
        stage.addActor(img)

        //Collection list
        buildCollectionList()

        scrollableCollections.width = 420f
        scrollableCollections.height = 700f
        scrollableCollections.setPosition(-5f, 210f)
        scrollableCollections.layout()
        scrollableCollections.setScrollingDisabled(true, false)
        scrollableCollections.setScrollbarsOnTop(false)
        scrollableCollections.setForceScroll(false, false)
        scrollableCollections.fadeScrollBars = false
        scrollableCollections.scrollTo(0f, collectionTable.height, 0f, 0f)
        stage.addActor(scrollableCollections)

        //Board Place
        boardPlace.setSize(1500f, 1080f)
        boardPlace.x = 420f
        stage.addActor(boardPlace)

        //Pack button WHITE
        packButtonWhite.x = 1920f - packButtonWhite.width - 20f
        packButtonWhite.y = 20f
        stage.addActor(packButtonWhite)
        packButtonWhite.addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 100f, packButtonWhite.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 200f, packButtonWhite.y, .3f)
                ))
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = false
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f)
                ))
            }
        })

        //Pack button RED
        packButtonRed.x = 1920f - packButtonRed.width - 20f
        packButtonRed.y = 20f
        stage.addActor(packButtonRed)
        packButtonRed.addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 100f, packButtonWhite.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 200f, packButtonWhite.y, .3f)
                ))
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = false
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f)
                ))
            }
        })

        //Pack button
        packButton.x = 1920f - packButton.width - 20f
        packButton.y = 20f
        stage.addActor(packButton)
        packButton.addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 100f, packButtonWhite.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x - 200f, packButtonWhite.y, .3f)
                ))
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = false
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f),
                ))
                packButtonWhite.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    },
                    Actions.moveTo(packButton.x, packButton.y, .3f)
                ))
            }
        })

        //Fake Pack button
        fakePackButton.x = 1920f - fakePackButton.width - 20f
        fakePackButton.y = 20f
        stage.addActor(fakePackButton)
        fakePackButton.addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    }
                ))
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                packButtonRed.addAction(SequenceAction(
                    Actions.run {
                        isHoveringPackButton = true
                    }
                ))
            }
        })

        //Shop button
        shopButton.x = 1920f - shopButton.width - 20f
        shopButton.y = 20f
        stage.addActor(shopButton)

        //Cards on hand button
        val cardsButton = ImageCache.getImage("ui/cardsbutton.png").apply {
            width = 150f
            height = 150f
        }
        cardsButton.x = 1920f - packButton.width - 20f
        cardsButton.y = cardsButton.height + 40f
        stage.addActor(cardsButton)
        handCardsQty.x = cardsButton.x + 20f
        handCardsQty.y = cardsButton.y
        stage.addActor(handCardsQty)
        cardsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (GameController.handCards.isNotEmpty()) {
                    handsCard2Dialog.show(stage)
                }
            }
        })

        //Playername
        val playerName = Label(GameController.getNickName(), UIController.skin, "Roboto-Bold-38")
        playerName.x = 20f
        playerName.y = 20f
        if (GameController.getNickName() == "1DEV1") {
            playerName.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    GameController.createNewPackage(PackageOrigin.DAILY, CardPackage.Type.REGULAR)
                }
            })
        }
        stage.addActor(playerName)

        //Credits
        creditsLabel.x = 20f
        creditsLabel.y = 80f
        stage.addActor(creditsLabel)

        //Album value
        albumValueLabel.x = 20f
        albumValueLabel.y = 140f
        stage.addActor(albumValueLabel)

        //Ranking button
        val rankButton = ImageCache.getImage("ui/rankingbutton.png").apply {
            width = 70f
            height = 70f
        }
        rankButton.x = 330f
        rankButton.y = 127f
        stage.addActor(rankButton)
        rankButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                rankingDialog.show(stage)
            }
        })

        //Achievements button
        val achievButton = ImageCache.getImage("ui/achievementsbutton.png").apply {
            width = 100f
            height = 100f
        }
        achievButton.x = 300f
        achievButton.y = 20f
        stage.addActor(achievButton)
        tasksClaimQty.x = achievButton.x - 5f
        tasksClaimQty.y = achievButton.y - 5f
        stage.addActor(tasksClaimQty)
        achievButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                tasksDialog.show(stage)
            }
        })

        //Current Board
        boardPlace.addActor(currentBoard)

        val version = Label(GameController.VERSION, UIController.skin, "BLUE-14")
        version.x = 10f

        stage.addActor(version)

        GameController.checkForPackages()

        super.show()
    }

    private fun packbuttonCallback() {
        if (!GameController.closedPackages[CardPackage.Type.REGULAR]!!.isNullOrEmpty()) {
            openPackageDialog.cardPackage = GameController.closedPackages[CardPackage.Type.REGULAR]!!.first()
            openPackageDialog.show(stage)
        }
    }

    private fun redPackbuttonCallback() {
        if (!GameController.closedPackages[CardPackage.Type.RED]!!.isNullOrEmpty()) {
            openPackageDialog.cardPackage = GameController.closedPackages[CardPackage.Type.RED]!!.first()
            openPackageDialog.show(stage)
        }
    }

    private fun whitePackbuttonCallback() {
        if (!GameController.closedPackages[CardPackage.Type.WHITE]!!.isNullOrEmpty()) {
            openPackageDialog.cardPackage = GameController.closedPackages[CardPackage.Type.WHITE]!!.first()
            openPackageDialog.show(stage)
        }
    }

    private fun buildCollectionList() {
        collectionTable.clear()

        collectionTable.top()
        Family.values().forEachIndexed { index, it ->
            // Create the label and icon as before
            val label = Label(it.listName.toUpperCase(), UIController.skin, "Roboto-Bold-45").apply {
                wrap = true
            }
            val image = ImageCache.getImage(it.iconName)

            // Create a container for the label
            val labelContainer = Container(label).apply {
                align(Align.left)
                width(340f)
                height(80f)
                padLeft(20f)
            }

            // Create a container for the image
            val imageContainer = Container(image).apply {
                width(80f)
                height(80f)
                padRight(10f).padTop(10f).padBottom(10f)
            }

            // Create a container for the entire row
            val rowContainer = Table().apply {
                add(labelContainer).align(Align.left).width(340f)
                add(imageContainer).width(80f).height(80f)
                background = if (currentCollection == it) {
                    createBackground(ColorCache.getColor("de6605")) // Change the color as needed
                } else null
            }

            // Add click listener to the entire row
            rowContainer.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    changeBoard(it)
                }
            })

            // Add the row container to the collection table
            collectionTable.add(rowContainer).align(Align.left).fillX().height(80f)
            collectionTable.row()
        }
        collectionTable.pack()

        scrollableCollections.actor = collectionTable
        scrollableCollections.invalidate()
        scrollableCollections.layout()

    }

    private fun changeBoard(family: Family) {
        currentBoard.remove()
        currentCollection = family
        currentBoard = when (family.layout) {
            Family.Layout.GRID -> GridLayoutBoardGroup(family)
            Family.Layout.FREE -> FreeLayoutBoardGroup(family)
        }
        boardPlace.addActor(currentBoard)
        buildCollectionList()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.066f, 0.066f, 0.066f, 0f)

        packButton.valueToShow = GameController.closedPackages[CardPackage.Type.REGULAR]!!.size
        packButtonRed.valueToShow = GameController.closedPackages[CardPackage.Type.RED]!!.size
        packButtonWhite.valueToShow = GameController.closedPackages[CardPackage.Type.WHITE]!!.size
        fakePackButton.valueToShow = GameController.closedPackagesQty
        handCardsQty.setText(GameController.handCards.size)
        creditsLabel.setText("${GameController.playerCashFormatted}")
        albumValueLabel.setText("AV ${GameController.albumValueFormatted}")
        tasksClaimQty.setText("${TaskController.unclaimedQtyFormatted}")

        if (GameController.packsToAlert > 0) {
            NewPackageDialog().show(stage)
        }

        if (GameController.closedPackagesQty > 0) {
            if (GameController.albumValue < GeneralConfigs.RED_PACKAGE_UNBLOCK_VALUE) {
                fakePackButton.isVisible = false
                packButtonRed.isVisible = false
                packButtonWhite.isVisible = false
                packButton.isVisible = true
            } else {
                if (isHoveringPackButton) {
                    fakePackButton.isVisible = false
                    packButton.isVisible = true
                    packButtonRed.isVisible = true
                    packButtonWhite.isVisible = true
                } else {
                    fakePackButton.isVisible = true
                    packButton.isVisible = false
                    packButtonRed.isVisible = false
                    packButtonWhite.isVisible = false
                }
            }
            shopButton.isVisible = false
        } else {
            fakePackButton.isVisible = false
            packButton.isVisible = false
            packButtonRed.isVisible = false
            packButtonWhite.isVisible = false
            shopButton.isVisible = true
        }

        super.render(delta)
    }

    private fun createBackground(color: Color): TextureRegionDrawable {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fill()
        val texture = Texture(pixmap)
        pixmap.dispose()
        return TextureRegionDrawable(texture)
    }

    private fun closeHandDialogCallBack() {
        changeBoard(Family.KILOWATT_CASE)
    }

}