# Used to show the speed difference

import time


def millis():
    return int(round(time.time() * 1000))


startTime = millis()
a = 20151125
x = 1
y = 1
while True:
    if y == 1:
        y = x + 1
        x = 1
    else:
        y -= 1
        x += 1
    a = (a * 252533) % 33554393

    if x == 3029 and y == 2947:
        print(a)
        break
print(str(millis() - startTime) + " ms")

