import sys
from datetime import datetime
import data
import operations


def cmd_slots(target_date):
    # Сверить расписание и свободные слоты
    slots = data.load("slots")
    free = operations.find_free_slot(slots, target_date, datetime.now())
    if not free:
        print(f"На {target_date} свободных слотов нет.")
        return
    print(f"Свободные слоты на {target_date}:")
    for s in free:
        print(f"  {s['slot_id']}  {s['time_start']}")


def cmd_check(request):
    # Проверить комплектность заявки
    scenarios = data.load("scenarios")
    slots = data.load("slots")
    bookings = data.load("bookings")
    print(operations.check_request(request, scenarios, slots, bookings))


def cmd_book(request):
    # Зафиксировать слот за клиентом
    bookings = data.load("bookings")
    slots = data.load("slots")
    booking, msg = operations.book_slot(bookings, slots, request)
    data.save("bookings", bookings)
    data.save("slots", slots)
    print(msg)
    if booking:
        print(f" {booking}")


def cmd_close(booking_id, instructor_id):
    # Закрыть бронь
    bookings = data.load("bookings")
    sessions = data.load("sessions")
    session, msg = operations.close_booking(
        bookings, sessions, booking_id, instructor_id, datetime.now()
    )
    data.save("bookings", bookings)
    data.save("sessions", sessions)
    print(msg)
    if session:
        print(f" {session}")


def main():
    if len(sys.argv) < 2:
        print("Использование: python main.py {slots|check|book|close} ...")
        return
    cmd = sys.argv[1]

    if cmd == "slots":
        cmd_slots(sys.argv[2] if len(sys.argv) > 2 else datetime.now().strftime("%Y-%m-%d"))
    elif cmd == "check":
        # demo-заявка
        request = {
            "client_id": "C0001",
            "scenario_id": "SC01",
            "date": "2026-11-10",
            "time_start": "14:00",
            "participants_count": 4,
            "phone": "+7 999 123-45-67",
            "slot_id": "SL0003",
        }
        cmd_check(request)
    elif cmd == "book":
        request = {
            "client_id": "C0001",
            "scenario_id": "SC01",
            "date": "2026-11-10",
            "time_start": "14:00",
            "participants_count": 4,
            "slot_id": "SL0003",
        }
        cmd_book(request)
    elif cmd == "close":
        booking_id = sys.argv[2] if len(sys.argv) > 2 else "B0001"
        instructor = sys.argv[3] if len(sys.argv) > 3 else "E0001"
        cmd_close(booking_id, instructor)
    else:
        print(f"Неизвестная команда: {cmd}")


if __name__ == "__main__":
    main()
