# main.py - Entry point for interacting with the program.

# Pre-processes a substring that will be searched for,
# specifically by marking where characters are at.
def preprocess(substring: str) -> list[int]:
    sublen = len(substring)

    # Create a table for all ASCII characters
    character_indexes = [sublen] * 256
    
    # For characters in the substring, mark their indexes.
    for index in range(0, sublen - 1):
        # ord() gets the ASCII value of the substring character.
        char_index = ord(substring[index])
        character_indexes[char_index] = sublen - 1 - index
    
    return character_indexes

# Checks whether two strings are equivalent or not in reverse order.
def parity(string_1: str, string_2: str):
    index = len(string_1) - 1

    while string_1[index] == string_2[index]:
        if index == 0:
            return True
        
        index -= 1
    
    return False

# Searches for a substring inside of a string.
def search(string: str, substring: str) -> int:
    characters = preprocess(substring)
    skip = 0
    strlen = len(string)
    sublen = len(substring)
    
    while strlen - skip >= sublen:
        if parity(string[skip:skip + sublen], substring):
            return skip
        
        # If a bad character is found, skip past that
        # using the offset from the character indexes
        # table that we made during the pre-processing.
        bad_char = string[skip + sublen - 1]
        bad_char_value = ord(bad_char)

        skip += characters[bad_char_value]
    
    return -1

# Prompts the user with available options.
def select_options() -> int:
    print("\nBOYER-MOORE SEARCH INTERFACE:")
    print("1. Print all U.S. states.")
    print("2. Print all U.S. states that contain a specified substring.")
    print("3. Exit the program.\n")

    while True:
        try:
            selection = int(input("Select one of the options above (1-3): "))

            if selection > 0 and selection < 4: 
                return selection

        except ValueError:
           pass
            
        print("Invalid input. Please select an integer value between 1 and 3.\n")

if __name__ == "__main__":
    states = [
        "alaska",
        "alabama",
        "arkansas",
        "arizona",
        "california",
        "colorado",
        "connecticut",
        "delaware",
        "florida",
        "georgia",
        "hawaii",
        "iowa",
        "idaho",
        "illinois",
        "indiana",
        "kansas",
        "kentucky",
        "louisiana",
        "massachusetts",
        "maryland",
        "maine",
        "michigan",
        "minnesota",
        "missouri",
        "mississippi",
        "montana",
        "north carolina",
        "north dakota",
        "nebraska",
        "new hampshire",
        "new jersey",
        "new mexico",
        "nevada",
        "new york",
        "ohio",
        "oklahoma",
        "oregon",
        "pennsylvania",
        "rhode island",
        "south carolina",
        "south dakota",
        "tennessee",
        "texas",
        "utah",
        "virginia",
        "vermont",
        "washington",
        "wisconsin",
        "west virginia",
        "wyoming"
    ]

    while True:
        match select_options():
            # Print all of the states.
            case 1:
                for state in states:
                    print(state)

            # Print all of the states with a given substring.
            case 2:
                substring = input("Select a substring to search for: ")

                for state in states:
                    index = search(state, substring)

                    if index != -1:
                        print(f"{state} ({index})")

            # Exit the program.
            case 3:
                print("Have a great day!")
                break
