package com.example.p3_azheng8

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlin.math.abs

class GridHandler(context: Context, img: ImageView?): View(context){

    private var totalMatches : Int = 0
    private var horizontalMatch : Int = 0
    private var verticalMatch : Int = 0
    private var bitmap : Array<Bitmap>? = null  //Store bitmap of sprites
    private var grid : ArrayList<ArrayList<Orb>>? = null    //Store orbs in a 2D grid
    private var matchedOrbs : ArrayList<ArrayList<Int>>? = null
    private var element = listOf(Element.SUN, Element.WATER, Element.EARTH, Element.LIGHTNING, Element.YINGYANG)
    private var myPaint : Paint? = null

    //Variables to help map sprites to the grid
    private var gridImg : ImageView? = null
    private var gridX = 0.0F
    private var gridY = 0.0F
    private var pixX = 0
    private var pixY = 0

    //Keep track of touch event positions
    private var initialX = 0F
    private var initialY = 0F
    private var finalX = 0F
    private var finalY = 0F


    interface gameInterface{
        fun updateScore(matches: Int)
        fun updateTurns()
    }

    private var gameListener : gameInterface? = null


    init{
        //convert dp to pixel
        pixX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics).toInt()
        pixY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics).toInt()

        //initialize the bitmap array for sprite
        val p1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.water_orb), pixX, pixY, false)
        val p2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sun_orb), pixX, pixY, false)
        val p3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.earth_orb), pixX, pixY, false)
        val p4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.tomoe_orb), pixX, pixY, false)
        val p5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.yingyang_orb), pixX, pixY, false)
        bitmap = arrayOf<Bitmap>(p1, p2, p3, p4, p5)

        grid = ArrayList()
        matchedOrbs = ArrayList()
        myPaint = Paint()
        gridImg = img   //image view of the grid
        gridX = gridImg?.x ?: 0F//x coordinate of top left corner of grid
        gridY = gridImg?.y ?: 1030F  //y coordinate of top left corner of grid
        gameListener = context as gameInterface

        createGrid()
    }

    /**
     * Create a grid of orbs
     */
    private fun createGrid(){

        for(i  in 0..8){    //Number of rows : 9
            val tempGrid =  ArrayList<Orb>()

            for(j in 0..7){ //Number of columns : 8
                val x = (j * pixX) + gridX  //map sprites x coordinate to the grid
                val y = (i * pixY) + gridY  //map sprites y coordinate to the grid

                val orb = Orb(x, y, element.random())
                tempGrid.add(orb)
            }
            this.grid!!.add(tempGrid)
        }
        invalidate()
    }


    /**
     * Remove matched orbs from the grid
     * Removed Orbs are set to EMPTY
     */
    private fun removeOrbs(){
        var row : Int = 0
        var col : Int = 0

        for(i in 0..< matchedOrbs!!.size){
            row = matchedOrbs!![i][0]
            col = matchedOrbs!![i][1]
            grid!![row][col].setType(Element.EMPTY)
        }
    }


    /**
     * Handle touch events to determine direction of orb swaps
     */
    override fun onTouchEvent(event: MotionEvent): Boolean{
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                //Store the initial position of the touch event
                initialX = event.x
                initialY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                //Store the final position of the touch event
                finalX = event.x
                finalY = event.y

                //Swap Orbs and redraw canvas
                swapOrbs(initialX, initialY, finalX, finalY)
                gameListener?.updateTurns()
                invalidate()
                return true
            }
        }
        return true
    }


    /**
     * Swap orbs on the grid
     * @param initialX initial x position
     * @param initialY initial y position
     * @param finalX final x position
     * @param finalY final y position
     */
    private fun swapOrbs(initialX: Float, initialY: Float, finalX: Float, finalY: Float){
        val pxDistance = abs(initialX - finalX) //Distance between initial and final x position
        val pyDistance = abs(initialY - finalY) //Distance between initial and final y position

        val j = (abs(initialX - gridX) / pixX).toInt()  //Convert grid x  position to array index
        val i = (abs(initialY - gridY) / pixY).toInt()  //Convert grid y position to array index

        if(initialX > finalX && pxDistance >= 50){  //Move orb left
            val col = j-1    //column--
            println("left")

            //Update the orbs positions and type
            val tempType = grid!![i][j].getType()
            grid!![i][j].setType(grid!![i][col].getType())
            grid!![i][col].setType(tempType)

            //Check matches for first orb
            horizontalMatch = horizontalScore(i, col, tempType)
            verticalMatch = verticalScore(i, col, tempType)
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)

            //Check matches for second orb
            horizontalMatch = horizontalScore(i, j, grid!![i][j].getType())
            verticalMatch = verticalScore(i, j, grid!![i][j].getType())
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)
        }
        else if(initialX < finalX && pxDistance >= 50){ //Move orb right
            val col = j + 1    //Column++
            println("right")

            val tempType = grid!![i][j].getType()
            grid!![i][j].setType(grid!![i][col].getType())
            grid!![i][col].setType(tempType)

            horizontalMatch = horizontalScore(i, col, tempType)
            verticalMatch = verticalScore(i, col, tempType)
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)

            horizontalMatch = horizontalScore(i, j, grid!![i][j].getType() )
            verticalMatch = verticalScore(i, j, grid!![i][j].getType() )
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)
        }
        else if(initialY > finalY && pyDistance >= 50){ //Move orb up
            val row = i-1    //row--
            println("up")

            val tempType = grid!![i][j].getType()
            grid!![i][j].setType(grid!![row][j].getType())
            grid!![row][j].setType(tempType)

            horizontalMatch = horizontalScore(row, j, tempType)
            verticalMatch = verticalScore(row, j, tempType)
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)

            horizontalMatch = horizontalScore(i, j, grid!![i][j].getType() )
            verticalMatch = verticalScore(i, j, grid!![i][j].getType() )
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)

        }
        else if(initialY < finalY && pyDistance >= 50){ //Move orb down
            val row = i+1    //row++
            println("down")

            val tempType = grid!![i][j].getType()
            grid!![i][j].setType(grid!![row][j].getType())
            grid!![row][j].setType(tempType)

            horizontalMatch = horizontalScore(row, j, tempType)
            verticalMatch = verticalScore(row, j, tempType)
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)

            horizontalMatch = horizontalScore(i, j, grid!![i][j].getType() )
            verticalMatch = verticalScore(i, j, grid!![i][j].getType() )
            totalMatches = horizontalMatch + verticalMatch
            verifyMatch(totalMatches)
        }

        matchedOrbs?.clear()    //reset matched orbs
    }

    /**
     * Check if there are 3 or more matches
     * If match is found, notify listener to update score
     * @param matches number of matches
     */
    private fun verifyMatch(matches : Int){
        if(matches >= 3){
            removeOrbs()
            println("horizontal match:$horizontalMatch vertical match:$verticalMatch")
            gameListener?.updateScore(totalMatches)
        }
    }

    /**
     * Check for number of horizontal matches
     * @param rows current row position of orb
     * @param columns current column position of orb
     * @param type current type of orb
     */
    private fun horizontalScore(rows : Int, columns : Int, type : Element) : Int{
        var matches : Int = 0;

        //Minus 1 to exclude the current orb
        matches += matchLeft(rows, columns, type) - 1;
        matches += matchRight(rows, columns, type) - 1;

        //Return 0 if no matches are found
        if(matches < 2){
            return 0;
        }

        return matches + 1; //Add current orb back to total
    }

    /**
     * Check for number of vertical matches
     * @param rows current row position of orb
     * @param columns current column position of orb
     * @param type current type of orb
     */
    private fun verticalScore(rows : Int, columns : Int, type : Element) : Int{
        var matches : Int = 0;

        //Minus one to exclude the current orb
        matches += matchUp(rows, columns, type) - 1;
        matches += matchDown(rows, columns, type) - 1;

        //return 0 if no matches were found
        if(matches < 2){
            return 0;
        }

        return matches + 1; //Add current orb back to total
    }


    /**
     * Recursively check for matches in up direction
     * @param row current row position of orb
     * @param col current column position of orb
     * @param type current type of orb
     */
    private fun matchUp(row : Int, col : Int, type : Element): Int{
        var rows = row;

        //return if no more matches
        if(rows < 0 || type == Element.EMPTY || grid!![rows][col].getType() != type){
            return 0;
        }

        matchedOrbs?.add(arrayListOf(row, col)) //Add current orb to matched orbs
        return 1 + matchUp(--rows, col, type);
    }

    /**
     * Recursively check for matches in down direction
     * @param row current row position of orb
     * @param col current column position of orb
     * @param type current type of orb
     */
    private fun matchDown(row : Int, col : Int, type : Element): Int{
        var rows = row;

        //return if no more matches
        if(rows == grid!!.size || type == Element.EMPTY || grid!![rows][col].getType() != type){
            return 0;
        }

        matchedOrbs?.add(arrayListOf(rows, col))

        return 1+ matchDown(++rows, col, type);
    }

    /**
     * Recursively check for matches in right direction
     * @param row current row position of orb
     * @param col current column position of orb
     * @param type current type of orb
     */
    private fun matchRight(row : Int, col : Int, type : Element): Int{
        var columns = col;

        //return if no more matches
        if(columns == grid!![0].size || type == Element.EMPTY || grid!![row][columns].getType() != type){
            return 0;
        }
        matchedOrbs?.add(arrayListOf(row, col))
        return 1 + matchRight(row, ++columns, type);
    }

    /**
     * Recursively check for matches in left direction
     * @param row current row position of orb
     * @param col current column position of orb
     * @param type current type of orb
     */
    private fun matchLeft(row : Int, col : Int, type : Element): Int{
        var columns = col;

        //return if no more matches
        if(columns < 0  || type == Element.EMPTY || grid!![row][columns].getType() != type){
            return 0;
        }

        matchedOrbs?.add(arrayListOf(row, col))
        return 1 + matchLeft(row, --columns, type);
    }


    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        var orb : Orb

        for(i in 0..8) {
            for (j in 0..7) {
                orb = grid!![i][j]

                when(orb.getType()){
                    Element.WATER -> {
                        canvas.drawBitmap(bitmap!![0], orb.getX(), orb.getY(), myPaint)
                    }
                    Element.SUN -> {
                        canvas.drawBitmap(bitmap!![1], orb.getX(),orb.getY(), myPaint)
                    }
                    Element.EARTH -> {
                        canvas.drawBitmap(bitmap!![2], orb.getX(), orb.getY(), myPaint)
                    }
                    Element.LIGHTNING -> {
                        canvas.drawBitmap(bitmap!![3],orb.getX(),orb.getY(), myPaint)
                    }
                    Element.YINGYANG -> {
                        canvas.drawBitmap(bitmap!![4], orb.getX(), orb.getY(), myPaint)
                    }
                    Element.EMPTY -> continue
                }
            }
        }

    }

}

