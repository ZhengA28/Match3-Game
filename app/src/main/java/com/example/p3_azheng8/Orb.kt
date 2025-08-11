package com.example.p3_azheng8

class Orb (x: Float, y: Float, type: Element){
    private var x: Float = 0.0F
    private var y: Float = 0.0F
    private var type: Element

    init {
        this.x = x
        this.y = y
        this.type = type
    }


    //Getter functions
    fun getX(): Float{ return x }
    fun getY(): Float{ return y }
    fun getType(): Element{ return type }

    //Setter functions
    fun setX(x: Float){ this.x = x }
    fun setY(y: Float){ this.y = y }
    fun setType(type: Element){ this.type = type }

}