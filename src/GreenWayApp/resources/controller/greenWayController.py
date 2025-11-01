import sqlite3

conn = sqlite3.connect('meu_banco.db')
cursor = conn.cursor()

cursor.execute('''
CREATE TABLE IF NOT EXISTS produtos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    preco REAL,
    estoque INTEGER
)
''')

cursor.execute("INSERT INTO produtos (nome, preco, estoque) VALUES (?, ?, ?)", 
               ('Notebook', 2500.90, 10))
cursor.execute("INSERT INTO produtos (nome, preco, estoque) VALUES (?, ?, ?)", 
               ('Mouse', 45.50, 50))

conn.commit()

print("=== TODOS OS PRODUTOS ===")
cursor.execute("SELECT * FROM produtos")
for linha in cursor.fetchall():
    print(linha)

cursor.execute("UPDATE produtos SET preco = ? WHERE nome = ?", (2600.00, 'Notebook'))
conn.commit()

cursor.execute("DELETE FROM produtos WHERE estoque = ?", (0,))
conn.commit()

conn.close()