class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :name, :null => false
      t.string :last_name, :null => false
      t.date :birth_date, :null => false
      t.string :name, :null => false
      t.integer :deleted, :null => false, :default => 0
    end
  end
end
