class Place < ActiveRecord::Base
  attr_accessible :latitude, :longitude, :name
end
