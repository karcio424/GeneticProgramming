import sys

def funkcja(text):
    new_text=''
    for i in range(len(text)):
        val=text[i]
        new_text+=val
        # print(val)
        if((val=='}' or val==';')and i<len(text)-1):
            if val == '}' and text[i+1:i+5]!='else':
                new_text+='\n'
            if val == ';' and text[i+1]!='}':
                new_text+='\n'
                
    return new_text

# if len(sys.argv) != 2:
#     print("Użycie: python3 .\\upraszczacz.py text_do_uproszczenia")
# else:
text = input("Enter the text to simplify: ")
wynik = funkcja(text)
print(wynik)