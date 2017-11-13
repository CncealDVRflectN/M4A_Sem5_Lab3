fun calcFunction(x: Double, y: Double): Double {
    return (Math.pow(y, 2.0) * Math.log(x) - y) / x
}

fun calcFunctionYDeriv(x: Double, y: Double): Double {
    return (2 * y * Math.log(x) - 1) / x
}

fun explicitEulerMethod(nodes: Vector, step: Double ,initialY: Double): Vector {
    val result = Vector(nodes.size)

    result[0] = initialY
    for (i in 0 until nodes.size - 1) {
        result[i + 1] = result[i] + step * calcFunction(nodes[i], result[i])
    }

    return result
}

fun calcImplicitEulerFunction(x: Double, y: Double, yPrev: Double, step: Double): Double {
    return y - yPrev - step * calcFunction(x, y)
}

fun calcImplicitEulerFunctionYDeriv(x: Double, y: Double, step: Double): Double {
    return 1 - step * calcFunctionYDeriv(x, y)
}

fun implicitEulerMethod(nodes: Vector, step: Double, initialY: Double, accuracy: Double): Vector {
    val result = Vector(nodes.size)
    var yCur: Double
    var yNext: Double

    result[0] = initialY
    for (i in 1 until nodes.size) {
        yNext = result[i - 1]
        do {
            yCur = yNext
            yNext = yCur - calcImplicitEulerFunction(nodes[i], yCur, result[i - 1], step) /
                    calcImplicitEulerFunctionYDeriv(nodes[i], yCur, step)
        } while (Math.abs(yNext - yCur) >= accuracy)
        result[i] = yNext
    }

    return result
}

fun main(args: Array<String>) {
    val intervalBottom = 1.0
    val intervalUpper = 2.0
    val initialX = 1.0
    val initialY = 1.0
    val nodeNum = 10
    val step = (intervalUpper - intervalBottom) / nodeNum
    val nodes = Vector(nodeNum + 1)
    val accuracy = Math.pow(10.0, -3.0)

    for (i in 0..nodeNum) {
        nodes[i] = intervalBottom + i * step
    }

    println("Явный метод Эйлера: ")
    print("Узлы: ")
    nodes.print()
    println("Значения искомой функции в узлах: ")
    explicitEulerMethod(nodes, step, initialY).print()
    println()

    println("Неявный метод Эйлера: ")
    print("Узлы: ")
    nodes.print()
    println("Значения искомой функции в узлах: ")
    implicitEulerMethod(nodes, step, initialY, accuracy).print()
    println()
}