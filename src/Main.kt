fun calcFunction(x: Double, y: Double): Double {
    return (Math.pow(y, 2.0) * Math.log(x) - y) / x
}

fun calcFunctionYDeriv(x: Double, y: Double): Double {
    return (2 * y * Math.log(x) - 1) / x
}

fun explicitEulerMethod(nodes: Vector, step: Double ,initialY: Double): Vector {
    val result = Vector(nodes.size)

    result[0] = initialY
    for (i in 1 until nodes.size) {
        result[i] = result[i - 1] + step * calcFunction(nodes[i - 1], result[i - 1])
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

fun sequentialAccuracyImprovementMethod(nodes: Vector, step: Double, initialY: Double): Vector {
    val result = Vector(nodes.size)

    result[0] = initialY
    for (i in 1 until nodes.size) {
        result[i] = result[i - 1] + step * (calcFunction(nodes[i - 1], result[i - 1]) +
                calcFunction(nodes[i - 1], result[i - 1] + step * calcFunction(nodes[i - 1], result[i - 1]))) / 2
    }

    return result
}

fun RungeKuttaMethod(nodes: Vector, step: Double, initialY: Double): Vector {
    val result = Vector(nodes.size)
    var fi = Vector(3)

    result[0] = initialY
    for (i in 1 until nodes.size) {
        fi[0] = step * calcFunction(nodes[i - 1], result[i - 1])
        fi[1] = step * calcFunction(nodes[0] + (i - 0.5) * step, result[i - 1] + fi[0] / 2)
        fi[2] = step * calcFunction(nodes[0] + (i - 1/3) * step, result[i - 1] + 2 * fi[1] / 3)
        result[i] = result[i - 1] + (fi[0] + 3 * fi[2]) / 4
    }

    return result
}

fun AdamsInterpolationMethod(nodes: Vector, step: Double, initialYFirst: Double, initialYSecond: Double): Vector {
    val result = Vector(nodes.size)
    var yCur: Double
    var yNext: Double

    result[0] = initialYFirst
    result[1] = initialYSecond
    for (i in 2 until nodes.size) {
        yNext = result[i - 1]
        do {
            yCur = yNext
            yNext = result[i - 1] + step * (5 * calcFunction(nodes[i], yCur) + 8 * calcFunction(nodes[i - 1], result[i - 1]) -
                    calcFunction(nodes[i - 2], result[i - 2])) / 12
        } while (Math.abs(yNext - yCur) >= Math.pow(step, 5.0))
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

    println("Метод последовательных приближений: ")
    print("Узлы: ")
    nodes.print()
    println("Значения искомой функции в узлах: ")
    sequentialAccuracyImprovementMethod(nodes, step, initialY).print()
    println()

    println("Метод Рунге-Кутта: ")
    print("Узлы: ")
    nodes.print()
    println("Значения искомой функции в узлах: ")
    RungeKuttaMethod(nodes, step, initialY).print()
    println()

    println("Интерполяционный метод Адамса: ")
    print("Узлы: ")
    nodes.print()
    println("Значения искомой функции в узлах: ")
    AdamsInterpolationMethod(nodes, step, initialY, 0.9165124387597267).print()
    println()
}