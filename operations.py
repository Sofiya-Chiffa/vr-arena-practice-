from datetime import datetime, timedelta
from algorithms import validate_request, find_free_slots


def check_request(request, scenarios, slots, bookings):
    # Операция 1. Проверить комплектность заявки.
    return validate_request(request, scenarios, slots, bookings)


def find_free_slot(slots, target_date, current_dt):
    # Операция 2. Сверить расписание и свободные слоты.
    return find_free_slots(slots, target_date, current_dt)


def book_slot(bookings, slots, request):
    # Операция 3. Зафиксировать слот за клиентом.
    slot_id = request["slot_id"]
    slot = None
    for s in slots:
        if s["slot_id"] == slot_id:
            slot = s
            break
    if slot is None:
        return None, "Слот не найден"
    if slot["status"] != "free":
        return None, "Слот уже занят"
    new_booking = {
        "booking_id": f"B{len(bookings) + 1:04d}",
        "client_id": request["client_id"],
        "slot_id": slot_id,
        "scenario_id": request["scenario_id"],
        "participants_count": int(request["participants_count"]),
        "status": "active",
    }
    bookings.append(new_booking)
    slot["status"] = "booked"
    return new_booking, "Бронь создана"


def close_booking(bookings, sessions, booking_id, instructor_id, now):
    # Закрыть бронь.
    booking = None
    for s in bookings:
        if s["booking_id"] == booking_id:
            booking = s
            break
    if booking is None:
        return None, "Бронь не найдена"
    if booking["status"] != "active":
        return None, "Бронь уже закрыта или отменена"

    booking["status"] = "closed"
    session = {
        "session_id": f"S{len(sessions) + 1:04d}",
        "booking_id": booking_id,
        "instructor_id": instructor_id,
        "actual_start": now.strftime("%Y-%m-%d %H:%M"),
        "actual_end": (now + timedelta(minutes=60)).strftime("%Y-%m-%d %H:%M"),
        "status": "completed",
    }
    sessions.append(session)
    return session, "Сеанс проведён, услуга оказана"