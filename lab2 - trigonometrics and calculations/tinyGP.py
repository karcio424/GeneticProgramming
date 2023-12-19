import os
import random
from math import sin, cos

class TinyGP:
    # ... (rest of your TinyGP code)

    @staticmethod
    def tekst_pliku(filename):
        # Adjust the folder paths accordingly
        file_path = os.path.join("./files/2", filename)
        print(file_path)
        if "\\data\\" in file_path:
            return file_path.replace("\\data\\", "\\done\\done ")
        print(file_path)
        return file_path

    # ... (rest of your TinyGP code)

    def zapis_do_pliku(self, tekst_do_zapisu):
        # Adjust the folder path accordingly
        output_path = os.path.join("./files/3", self.PLIK)
        with open(output_path, "a") as file:
            file.write(tekst_do_zapisu)

# Example usage
filename = "lab2fun1dzi1 0.0 6.283184 0.03141592.dat"
filename = TinyGP.tekst_pliku(filename)
filename = f'"{filename}"'  # Enclose the filename in double quotes
seed = -1
tiny_gp = TinyGP(filename, seed)
tiny_gp.evolve()
