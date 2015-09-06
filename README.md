A simple calculator written in Java. Some sample commands:
add(1, mult(2, 3)) -> 7
mult(add(2, 5), div(10, 2)) -> 35
let(a, 6, mult(3, a)) -> 18
let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))) -> 40

SAMPLE INPUT:
java ju.michael.app.calc "add(1, 2)"
