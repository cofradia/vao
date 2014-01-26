class CreateEventComments < ActiveRecord::Migration
  def change
    create_table :event_comments do |t|

      t.timestamps
    end
  end
end
