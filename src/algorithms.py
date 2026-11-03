from datetime import datetime, timedelta


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
PREP_MINUTES = 15

def parse_dt(date_str, time_str):
    return datetime.strptime(f"{date_str} {time_str}", "%Y-%m-%d %H:%M")

def validate_request(request, scenarios, slots, bookings):
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
    target_slot = None
    for slot in slots:
        if slot["slot_id"] == request["slot_id"]:
            target_slot = slot
            break
    if target_slot is None:
        return f"Ошибка: слот {request['slot_id']} не найден"
    if target_slot["status"] != "free":
        return f"Ошибка: слот {request['slot_id']} уже занят"
    target_start = parse_dt(target_slot["date"], target_slot["time_start"])
    duration = scenario["duration_min"]
    target_end = target_start + timedelta(minutes=(duration + PREP_MINUTES))
    for slot in slots:
        if slot["date"] != target_slot["date"] or slot["status"] == "free":
            continue
        slot_start = parse_dt(slot["date"], slot["time_start"])
        for booking in bookings:
            if booking["slot_id"] != slot["slot_id"]:
                continue
            if booking["status"] != "active":
                continue
            booking_scenario = None
            for s in scenarios:
                if s["scenario_id"] == booking["scenario_id"]:
                    booking_scenario = s
                    break
            booking_duration = booking_scenario["duration_min"]
            slot_end = slot_start + timedelta(minutes=(booking_duration + PREP_MINUTES))
            if not (target_end <= slot_start or slot_end <= target_start):
                return (
                    f"Ошибка: желаемое время пересекается с бронью "
                    f"{booking['booking_id']} (слот {slot['slot_id']})"
                )
    if int(request["participants_count"]) > scenario["max_participants"]:
        return (
            f"Ошибка: количество участников {request['participants_count']} "
            f"превышает допустимый максимум {scenario['max_participants']}"
        )
    phone_digits = "".join(ch for ch in str(request["phone"]) if ch.isdigit())
    if len(phone_digits) != 11:
        return "Ошибка: некорректный формат номера телефона"
    return "Заявка корректна"
