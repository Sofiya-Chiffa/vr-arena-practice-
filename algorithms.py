"""Алгоритмы КТ2. Ветвление + вложенный цикл / сортировка."""
from datetime import datetime


def find_free_slots(slots, target_date, current_dt):
    # Формирует список свободных слотов на target_date и сортирует по времени.
    result = []
    for slot in slots:
        if slot["date"] != target_date or slot["status"] != "free":
            continue
        if slot["date"] == current_dt.strftime("%Y-%m-%d"):
            slot_dt = datetime.strptime(
                slot["date"] + " " + slot["time_start"], "%Y-%m-%d %H:%M"
            )
            if slot_dt <= current_dt:
                continue
        result.append(slot)
    result.sort(key=lambda s: s["time_start"])
    return result


REQUIRED_FIELDS = ("scenario_id", "date", "time_start", "participants_count", "phone")


def validate_request(request, scenarios):
    # Валидация заявки клиента.
    for field in REQUIRED_FIELDS:
        if field not in request or request[field] in ("", None):
            return f"Ошибка: пустое поле '{field}'"
    scenario = None
    for s in scenarios:
        if s["scenario_id"] == request["scenario_id"]:
            scenario = s
            break
    if scenario is None:
        return f"Ошибка: сценарий {request['scenario_id']} не найден"
    if int(request["participants_count"]) > scenario["max_participants"]:
        return (
            f"Ошибка: количество участников {request['participants_count']} "
            f"превышает допустимый максимум {scenario['max_participants']}"
        )
    phone_digits = "".join(ch for ch in str(request["phone"]) if ch.isdigit())
    if len(phone_digits) != 11:
        return "Ошибка: некорректный формат номера телефона"
    return "Заявка корректна"
