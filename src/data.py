import json
import os

DATA_DIR = os.path.join(os.path.dirname(__file__), "data")

ENTITIES = {
    "clients": "clients.json",
    "employees": "employees.json",
    "scenarios": "scenarios.json",
    "slots": "slots.json",
    "equipment": "equipment.json",
    "bookings": "bookings.json",
    "sessions": "sessions.json",
}


def _path(name):
    return os.path.join(DATA_DIR, ENTITIES[name])


def load(name):
    # Загружает список записей сущности name
    with open(_path(name), "r", encoding="utf-8") as f:
        return json.load(f)


def save(name, records):
    # Сохраняет список записей сущности name.
    with open(_path(name), "w", encoding="utf-8") as f:
        json.dump(records, f, ensure_ascii = False, indent = 2)

    def load_all():
        # Возвращает словарь {имя_сущности: список записей}.
        return {name: load(name) for name in ENTITIES}
