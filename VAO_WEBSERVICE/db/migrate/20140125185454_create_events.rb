class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :name
      t.string :description
      t.string :rating
      t.integer :likes 
      t.integer :mood 
      t.integer :deleted

      t.timestamps
    end
  end
end
