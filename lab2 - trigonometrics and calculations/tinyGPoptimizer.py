from sympy import simplify, cos, sin, symbols
import sys

X1, X2= symbols('X1 X2')

print(len(sys.argv))

if len(sys.argv) != 2:
    print("Użycie: python3 .\\tinyGPoptimizer.py nazwa_pliku")
else:
    plik = sys.argv[1]
    with open(plik, 'r') as f:
        text = f.read()

    print(f)
    output = "simplified" + plik[1:]

    # text = "((4.294824698455102*(X1/(-4.532855240156618/0.02722632543021053)))*-0.17702714792331875)*(((4.286692986806084*-2.295286580145861)+(0.7165152312258236/(-1.3124560181182643*(X1/(-3.0521192366115235/0.02722632543021053)))))+((((((((X1/(-3.268266518937233/0.02722632543021053))/-2.4828720491828316)*cos((((4.294824698455102*(X1/(-3.9797675297314217/0.02722632543021053))) ))))))))))"
    solution = simplify(text, ratio=1.7)
    print("Simplified solution:", solution)

    with open(output, 'w') as f:
        f.write(solution)