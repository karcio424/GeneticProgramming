from sympy import simplify, cos, sin, symbols, expand
import sys
import os

X1, X2= symbols('X1 X2')

if len(sys.argv) != 2:
    print("Użycie: python3 .\\tinyGPoptimizer.py nazwa_pliku")
else:
    plik = sys.argv[1]
    with open(plik, 'r') as f:
        text = f.read()
    print("INPUT TEXT:\n", text)
    solution = str(expand(simplify(text, ratio=1.7)))
    print("SIMPLER SOLUTION:\n", solution)

    if "\\files" in plik:   #dla naszego przypadku w folderze lab2
        dir_simplified = "6 - tinyGP_simplified"
 
        output = plik.replace("3 - tinyGP_calculated",dir_simplified)
        output = output.replace("done","simple")

        with open(output, 'w') as f:
            f.write(solution)