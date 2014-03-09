class CreateEventDates < ActiveRecord::Migration
  def change
    create_table :event_dates do |t|
    	t.datetime :start_date
    	t.datetime :end_date
    	t.integer :event_id
      t.timestamps
    end
  end
end
