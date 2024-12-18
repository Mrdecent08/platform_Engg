import tiktoken
import sys

def count_tokens(text):
    enc = tiktoken.get_encoding("cl100k_base")
    tokens = enc.encode(text)
    return tokens, len(tokens)

if __name__ == "__main__":
    input_text = sys.argv[1]  # Accept input text from command-line argument
    tokens, token_count = count_tokens(input_text)
    print(f"{token_count}")