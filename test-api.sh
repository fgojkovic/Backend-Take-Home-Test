#!/bin/bash

# Get the current date and calculate the start date (30 days ago)
CURRENT_DATE=$(date +%Y-%m-%d)  # 2025-05-25
START_DATE=$(date -d "30 days ago $CURRENT_DATE" +%Y-%m-%d)  # 2025-04-25

# Extract year, month, and day from current and start dates
CURRENT_YEAR=$(date -d "$CURRENT_DATE" +%Y)  # 2025
CURRENT_MONTH=$(date -d "$CURRENT_DATE" +%m) # 05
CURRENT_DAY=$(date -d "$CURRENT_DATE" +%d)   # 25
START_YEAR=$(date -d "$START_DATE" +%Y)      # 2025
START_MONTH=$(date -d "$START_DATE" +%m)     # 04
START_DAY=$(date -d "$START_DATE" +%d)       # 25

# Array to store morning feelings
MORNING_FEELINGS=("GOOD" "BAD" "OK")

# Initialize the sleep_logs array
sleep_logs=()

# Function to get the number of days in a month
days_in_month() {
    date -d "$1-01 + 1 month - 1 day" +%d
}

# Loop through each day from START_DATE to CURRENT_DATE
CURRENT_DATE_EPOCH=$(date -d "$CURRENT_DATE" +%s)
START_DATE_EPOCH=$(date -d "$START_DATE" +%s)
for ((epoch=START_DATE_EPOCH; epoch<=CURRENT_DATE_EPOCH; epoch+=86400)); do
    DATE=$(date -d "@$epoch" +%Y-%m-%d)
    YEAR=$(date -d "$DATE" +%Y)
    MONTH=$(date -d "$DATE" +%m)
    DAY=$(date -d "$DATE" +%d)

    # Generate random times
    START_HOUR=$((RANDOM % 2 + 22))  # 22 or 23
    START_MINUTE=$((RANDOM % 60))
    START_SECOND=$((RANDOM % 60))
    
    # timeInBedEnd: Next day, random hour between 5 and 8
    END_HOUR=$((RANDOM % 4 + 5))  # 5 to 8
    END_MINUTE=$((RANDOM % 60))
    END_SECOND=$((RANDOM % 60))
    
    # Calculate the next day for timeInBedEnd
    NEXT_DATE=$(date -d "$DATE + 1 day" +%Y-%m-%d)
    NEXT_YEAR=$(date -d "$NEXT_DATE" +%Y)
    NEXT_MONTH=$(date -d "$NEXT_DATE" +%m)
    NEXT_DAY=$(date -d "$NEXT_DATE" +%d)

    # Randomly select a morning feeling
    RANDOM_FEELING=${MORNING_FEELINGS[$((RANDOM % 3))]}

    # Construct the JSON payload
    JSON='{"timeInBedStart": "'$YEAR'-'$MONTH'-'$DAY'T'$START_HOUR':'$(printf "%02d" $START_MINUTE)':'$(printf "%02d" $START_SECOND)'.644","timeInBedEnd": "'$NEXT_YEAR'-'$NEXT_MONTH'-'$NEXT_DAY'T'$(printf "%02d" $END_HOUR)':'$(printf "%02d" $END_MINUTE)':'$(printf "%02d" $END_SECOND)'.644","morningFeeling": "'$RANDOM_FEELING'"}'

    # Add to the sleep_logs array
    sleep_logs+=("$JSON")
done

# Test 1: Create a sleep log for user with id 1
echo "Starting the Sleep API tests..."
echo "----------------------------------------"
echo "Test 1: Create a sleep log for user with id 1"
echo "----------------------------------------"   
# Print the array (for debugging)
echo "Generated sleep_logs array for the last 30 days (from $START_DATE to $CURRENT_DATE):"
printf '%s\n' "${sleep_logs[@]}"
echo "----------------------------------------"   
echo "Creating a sleep log for user with id 1..."
# Loop through the array and make POST requests
for log in "${sleep_logs[@]}"; do
    echo "Adding sleep log: $log"
    curl -X POST http://localhost:8080/api/sleep/1 \
         -H "Content-Type: application/json" \
         -d "$log"
    echo -e "\n"
done
echo "----------------------------------------"

# Test 2: Fetch last night sleep logs for user with id 1
echo "Test 2: Fetch last night sleep logs for user with id 1"
echo "----------------------------------------"
echo "Fetching last night sleep logs for with id 1..."
curl http://localhost:8080/api/sleep/1/last-night
echo -e "\n"
echo "----------------------------------------"

# Test 3: Fetch 30 day averages
echo "Test 3: Fetch 30 day averages for user with id 1"
echo "----------------------------------------"
echo "Fetching 30 day averages for user with id 1..."
curl http://localhost:8080/api/sleep/1/averages
echo -e "\n"
echo "----------------------------------------"
