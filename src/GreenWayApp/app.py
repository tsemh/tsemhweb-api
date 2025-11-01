import toga
from toga.style import Pack
from toga.style.pack import COLUMN, ROW, CENTER
from pathlib import Path
import os
from .resources.repository.greenWayRepository import GreenWayRepository

class GreenWay(toga.App):
    def __init__(self):
        super().__init__()
        self.repository = GreenWayRepository()

    def startup(self):
        user = self.repository.get_user_by_email("ana.silva@email.com")
        score = user['score']
        current_dir = Path(os.path.dirname(__file__))
        menu_path = current_dir / 'resources' / 'images' / 'menu'

        home_box = toga.Box(style=Pack(direction=COLUMN, margin=10, background_color='#000000', alignment=CENTER, flex=1))

        menu_superior = toga.Box(
            style=Pack(
                direction=ROW,
                padding=5,
                height=30,
                alignment=CENTER,
                margin_top=20,
                margin_bottom=20
            )
        )
        menu_superior.add(self.btn_addition(str(menu_path / 'config.png')))
        menu_superior.add(self.btn_addition(str(menu_path / 'perfil.png')))
        home_box.add(menu_superior)
        
        home_box.add(toga.Label(
            "Green Way",
            style=Pack(font_size=32, font_weight='bold', margin_bottom=10, color='#00FF00', text_align='center')
        ))

        gradient_path = current_dir / 'resources' / 'images' / 'gradient.png'
        gradient = toga.Image(str(gradient_path))
        img_view = toga.ImageView(gradient)
        img_view.style = Pack(alignment=CENTER)
        home_box.add(img_view)
        home_box.add(toga.Label(
            str(score), 
            style=Pack(font_size=32, color=self.scoreColor(score), text_align='center', alignment=CENTER, width=100, margin_top=-80)
        ))
        
        menu_inferior = toga.Box(
            style=Pack(
                direction=ROW, 
                margin=5, 
                alignment=CENTER,
                margin_top=80,
                margin_bottom=20
            )
        )
        
        btn_menu = ['Tarefas', 'Ranking', 'Home', 'Convide um amigo']
        for btn in btn_menu:
            icon_name = f"{btn.lower().replace(' ', '_')}.png"
            icon_path = menu_path / icon_name
            
            menu_inferior.add(self.btn_addition(str(icon_path)))
        home_box.add(menu_inferior)

        self.main_window = toga.MainWindow(title=self.formal_name)
        self.main_window.content = home_box
        self.main_window.show()
    
    def btn_switchScreen(self, widget, screen):
        print(f"Mudando para tela: {screen}")

    def btn_addition(self,icon_path):
        return toga.Button(
            icon=icon_path, 
            on_press=lambda widget: self.btn_switchScreen(widget, icon_path),
            style=Pack(
                height=30,
                padding=10,
                margin=(0, 5), 
                color='#000000', 
                flex=1,
                text_align='center'
            )
        )

    def scoreColor(self, score: int) -> str:
        colors = [
            (333, '#FFA500'),
            (445, '#FFC300'),
            (557, '#FFFF00'),
            (668, '#ADFF2F'),
            (1000, '#00FF00C3')
        ]
        return next((color for limit, color in colors if score <= limit), '#FFFFFF')

def main():
    return GreenWay()