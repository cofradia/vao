class Event < ActiveRecord::Base
  attr_accessible :name, :description, :id_category, :place_id, :user_id
end
